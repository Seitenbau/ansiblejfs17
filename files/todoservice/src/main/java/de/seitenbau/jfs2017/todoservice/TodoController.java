package de.seitenbau.jfs2017.todoservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.Lists;

@RestController
public class TodoController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  
	@Autowired
	TodoRepository repo;
	
	@GetMapping("/all")
	public List<TodoEntity> getAllTodos()
	{
		ArrayList<TodoEntity> todos = Lists.newArrayList(repo.findAll());
		log.info("Returning {} todos.", todos.size());
		return todos;
	}
	
	@PostMapping("/{name}")
	public ResponseEntity<Void> addTodo(@PathVariable String name, @RequestBody String description)
	{
	  log.info("Adding todo with name \"{}\" and description \"{}\".", name, description);
		TodoEntity todoToAdd = new TodoEntity(name, description);
		
		TodoEntity addedTodo = repo.save(todoToAdd);
		URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/{id}").buildAndExpand(addedTodo.id).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping("/{id}")
	public TodoEntity getById(@PathVariable Long id)
	{
	  log.info("Returning todo for id \"{}\"");
		return repo.findOne(id);
	}
}
