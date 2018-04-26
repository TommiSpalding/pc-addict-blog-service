package com.example.test.demo.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * CrudRespository for blogposts.
 */
public interface BlogpostRepository extends CrudRepository<Blogpost, Long> {

    /**
     * Finds blogposts by text body containing ignore case or title containing ignore case or author name containing ignore case list.
     *
     * @param str1 title
     * @param str2 author
     * @param str3 textbody
     * @return the list
     */
    List<Blogpost> findByTextBodyContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(String str1, String str2, String str3);

    /**
     * Finds blogposts by author name containing ignore case list.
     *
     * @param str1 author
     * @return the list
     */
    List<Blogpost> findByAuthorNameContainingIgnoreCase(String str1);
}
