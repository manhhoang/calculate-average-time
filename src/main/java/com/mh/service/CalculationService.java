package com.mh.service;

import com.mh.model.Task;

import java.util.concurrent.CompletableFuture;

public interface CalculationService {

    /**
     * Save the task to database
     * @param task
     * @return CompletableFuture<Task>
     */
    CompletableFuture<Task> create(Task task);

    /**
     * Find the task by task Id
     * @param taskId
     * @return CompletableFuture<Task>
     */
    CompletableFuture<Task> findByTaskId(String taskId);
}
