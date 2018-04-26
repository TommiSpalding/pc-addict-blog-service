package com.example.test.demo.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogpostRepository extends CrudRepository<Blogpost, Long> {

    Blogpost findByblogId(long blogId);
    List<Blogpost> findByTextBodyContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(String str1, String str2, String str3);
    List<Blogpost> findByAuthorNameContainingIgnoreCase(String str1);
}
