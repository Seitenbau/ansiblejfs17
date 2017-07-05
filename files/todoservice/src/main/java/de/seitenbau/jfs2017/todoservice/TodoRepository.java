package de.seitenbau.jfs2017.todoservice;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<TodoEntity, Long>
{
	

}
