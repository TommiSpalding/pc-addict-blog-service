package com.example.test.demo;

import org.springframework.data.repository.CrudRepository;

public interface BlogpostRepository extends CrudRepository<Blogpost, Long> {
}
