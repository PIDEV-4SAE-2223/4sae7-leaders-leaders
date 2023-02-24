package com.example.backend.generic;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class GenericController<T,ID> {
	@Autowired
	IGenericService<T,ID> genericService;

	@PostMapping()
	public T add(@RequestBody T entity) {
		return genericService.add(entity);
	}

	@PutMapping()
	public T update(@RequestBody T entity) {
		return	genericService.update(entity);
	}

	@GetMapping("/{id}")
	public T retrieveById(@PathVariable ID id ) {
		return	genericService.retrieveById(id);
	}

	@GetMapping()
	public List<T> retrieveAll() {
		return	genericService.retrieveAll();
	}

	@DeleteMapping("/{id}")
	public Boolean delete(@PathVariable ID id) {
		return	genericService.delete(id);
	}
}
