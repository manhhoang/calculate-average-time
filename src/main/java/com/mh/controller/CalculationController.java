package com.mh.controller;

import com.mh.model.Task;
import com.mh.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/api")
public class CalculationController {

    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @RequestMapping(value = "/v1/task", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public CompletableFuture<Task> createTask(@RequestBody Task task) {
        return this.calculationService.create(task);
    }

    @RequestMapping(value = "/v1/task/{taskId}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public CompletableFuture<Task> getTask(@PathVariable("taskId") String taskId) {
        return this.calculationService.findByTaskId(taskId);
    }

}
