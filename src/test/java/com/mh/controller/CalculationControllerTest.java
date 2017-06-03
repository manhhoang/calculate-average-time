package com.mh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setupMock() {

    }

    @Test
    public void testGetTaskByTaskIdWithZeroTask() throws IOException {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(header);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/task/1", String.class, entity);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.readValue(response.getBody(), Task.class);
        assertThat(task.getId(), equalTo(0));
        assertThat(task.getTaskId(), equalTo(""));
        assertThat(task.getDuration(), equalTo(0L));
    }
}
