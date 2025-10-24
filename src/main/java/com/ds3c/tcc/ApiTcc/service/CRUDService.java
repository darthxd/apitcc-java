package com.ds3c.tcc.ApiTcc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class CRUDService<T, ID> {
    protected final Class<T> entityClass;
    protected final JpaRepository<T, ID> repository;

    protected CRUDService(Class<T> entityClass, JpaRepository<T, ID> repository) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    public List<T> findAll() {return repository.findAll();}
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The entity "+entityClass.getName()+" with ID: "+id+" was not found."));
    }
    public T save(T entity) {return repository.save(entity);}
    public void delete(T entity) {repository.delete(entity);}
}
