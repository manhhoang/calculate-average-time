# Calculate Average Time

#### Run the service

```
mvn spring-boot:run
```

#### Run the service from Docker

```
docker run -p 8080:8080 -t manhhoang/calculate-average-time
```

#### Asynchronous Rest Api using CompletableFuture

```
@RequestMapping(value = "/v1/task/{taskId}", method = RequestMethod.GET, headers = "Accept=application/json")
@ResponseBody
public CompletableFuture<Task> getTask(@PathVariable("taskId") String taskId) {
    return this.calculationService.findByTaskId(taskId);
}
```

#### Asynchronous access to database and calculating the duration

```
public CompletableFuture<Task> findByTaskId(String taskId) {
    return CompletableFuture.supplyAsync(() -> {

        Optional<List<Task>> tasks = calculationRepository.findByTaskId(taskId);
        long totalDuration = 0;
        String foundTaskId = "";
        for(Task task : tasks.get()) {
            totalDuration += task.getDuration();
            foundTaskId = task.getTaskId();
        }
        int size = tasks.get().size();
        long averageDuration = size != 0 ? totalDuration/size : 0;

        return new Task(foundTaskId, averageDuration);
    });
}
```

#### Repository Domain

```
@Repository
public interface CalculationRepository extends CrudRepository<Task, Long> {

    Optional<List<Task>> findByTaskId(String taskId);
}
```

#### Junit Test

```
@Test
public void testFindByTaskIdWithOneTask() throws ExecutionException, InterruptedException {
    String taskId = "task_1";
    Optional<List<Task>> tasks = Optional.of(new ArrayList<>());
    Task task = new Task();
    task.setTaskId(taskId);
    task.setDuration(10000);
    tasks.get().add(task);
    when(calculationRepository.findByTaskId(taskId)).thenReturn(tasks);
    CompletableFuture<Task> futureTask = calculationService.findByTaskId(taskId);
    Task result = futureTask.get();
    assertEquals(result.getId(), 0);
    assertEquals(result.getTaskId(), taskId);
    assertEquals(result.getDuration(), 10000);
}
```

#### Integration Test

```
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
```

#### JPA Configuration using H2 database

```
# Connection url for the H2 database "calculate"
spring.datasource.url=jdbc:h2:file:~/calculate

# Username and password for H2
spring.datasource.username=sa
spring.datasource.password=sa
```

#### Swagger api documents

http://localhost:8080/v2/api-docs

http://localhost:8080/swagger-ui.html

```
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mh.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
```

#### Dockerfile

```
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD calculate-average-time-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```