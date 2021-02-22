package api.service;

import api.exception.AppException;
import api.models.Worker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WorkerService {
    private final List<Worker> workers = new ArrayList<>(
            List.of(
                    Worker.builder().name("Tony").salary(new BigDecimal("34000")).build(),
                    Worker.builder().name("Napoleon").salary(new BigDecimal("50000")).build(),
                    Worker.builder().name("Frida").salary(new BigDecimal("65000")).build()
            ));

    public Worker add(Worker worker) {
        workers.add(worker);
        return worker;
    }

    public List<Worker> findAll() {
        return workers;
    }

    public Worker findByName(String name) {
        if (name == null) {
            throw new AppException("Name is null");
        }

        return findAll().stream()
                .filter(employee -> employee.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AppException("Can not find worker with name: " + name));
    }

    public String deleteByName(String name) {
        var worker = findByName(name);
        findAll().remove(worker);
        return "Employee with name " + name + " deleted.";
    }

    public Worker update(Worker worker) {
        var currentEmployee = findByName(worker.getName());
        int index = findAll().indexOf(currentEmployee);
        findAll().set(index,
                Worker
                        .builder()
                        .name(worker.getName())
                        .salary(worker.getSalary())
                        .build());
        return worker;
    }
}
