package api.web;

import api.exception.AppException;
import api.models.ErrorMessage;
import api.models.Worker;
import api.service.WorkerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import static spark.Spark.*;


@RequiredArgsConstructor
public class Routing {

    private final WorkerService service;

    public void initRoutes() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        before(((request, response) -> {
            System.out.println("BEFORE");
            System.out.println(request.uri());
        }));

        path("/employees", () -> {
            get("", (request, response) -> {
                var employees = service.findAll();
                response.status(200);
                response.header("Content-Type", "application/json;charset=utf-8");
                return employees;
            }, new JsonTransformer());

            path("/name", () -> {
                get("/:name", (request, response) -> {
                    response.status(200);
                    response.header("Content-Type", "application/json;charset=utf-8");
                    return service.findByName(request.params("name"));
                }, new JsonTransformer());
            });

            post("/create", (request, response) -> {
                var employee = gson.fromJson(request.body(), Worker.class);
                response.status(201);
                response.header("Content-Type", "application/json;charset=utf-8");
                return service.add(employee);
            }, new JsonTransformer());

            //http://localhost:8081/employees/delete?name=Tony
            delete("/delete", (request, response) -> {
                var name = request.queryParams("name");
                response.status(200);
                return service.deleteByName(name);
            });

            //http://localhost:8081/employees
            put("/update", (request, response) -> {
                var employee = gson.fromJson(request.body(), Worker.class);
                response.status(200);
                response.header("Content-Type", "application/json;charset=utf-8");
                return service.update(employee);
            }, new JsonTransformer());

        });

        exception(AppException.class, ((e, request, response) -> {
            response.header("error", e.getMessage());
            response.redirect("/error", 301);
        }));

        path("/error", () -> {
            get("", ((request, response) -> {
                response.header("Content-Type", "application/json;charset=utf-8");
                return gson.toJson(ErrorMessage
                        .builder()
                        .message("APP EXCEPTION")
                        .build());
            }));
        });
    }
}
