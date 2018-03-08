package com.example.test.demo;

public interface DemoRepository<T, ID> {

    public T saveEntity(T entity);
    public void delete(ID id) throws IllegalArgumentException;
    public Iterable<T> findAll();
    public T findOne(ID id);
}
