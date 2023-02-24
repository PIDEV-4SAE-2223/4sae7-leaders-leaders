package com.example.backend.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface IGenericService<T,ID> {

	T add(T entity);
	T update(T entity);
	T retrieveById(ID id);
	List<T> retrieveAll();
	Boolean delete(ID id);
	
}
