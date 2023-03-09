package com.example.backend.Services;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IService<Object> {

    ResponseEntity<?> create(Object saved);
    ResponseEntity<?> update(Object saved,Long id);
    ResponseEntity<?>  findById(Long id) ;
    ResponseEntity<?> findAll();
          List<Object> deleteById(Long Id);

}
