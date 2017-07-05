package de.seitenbau.jfs2017.todoservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class TodoEntity {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String name;
	String description;
	
	protected TodoEntity()
	{}
	
	public TodoEntity(String name, String description)
	{
		this.name = name;
		this.description = description;
	}
}
