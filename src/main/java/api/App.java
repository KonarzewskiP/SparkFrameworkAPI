package api;

import api.service.WorkerService;
import api.web.Routing;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.port;

public class App {

    public static void main(String[] args) {
        initExceptionHandler(e -> System.out.println(e.getMessage()));
        port(8081);
        var workerService = new WorkerService();
        var routing = new Routing(workerService);
        routing.initRoutes();
    }
}
