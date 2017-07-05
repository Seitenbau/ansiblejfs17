package de.seitenbau.jfs2017.todoservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TodoControllerTest
{

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void canQueryEmptyTodos()
  {
    // when
    ResponseEntity<TodoEntity[]> responseEntity = restTemplate.getForEntity("/all", TodoEntity[].class);

    // then
    Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    assertThat(responseEntity.getBody()).isEmpty();
  }

  @Test
  public void canQueryAddedTodo()
  {
    // given
    String expectedName = "aName";
    String expectedDescription = "aDescription";

    HttpEntity<String> request = new HttpEntity<>(expectedDescription);
    URI result = restTemplate.postForLocation("/"+expectedName, request, TodoEntity.class);
    assertThat(result, notNullValue());

    // when
    TodoEntity responseEntity = restTemplate.getForObject(result, TodoEntity.class);

    // then
    assertThat(responseEntity.getName()).isEqualTo(expectedName);
    assertThat(responseEntity.getDescription()).isEqualTo(expectedDescription);
  }
  
}
