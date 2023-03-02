package com.example.backend.Services;

import java.util.List;

public interface IService<Object> {

        Object create(Object saved);
          Object findById(Long id) ;
          List<Object> findAll();
          List<Object> deleteById(Long Id);

}
