package com.mh.service;

import com.mh.model.Task;

import java.util.concurrent.CompletableFuture;

public interface CalculationService {

    CompletableFuture<Task> create(Task task);

    CompletableFuture<Task> findByTaskId(String taskId);
}
