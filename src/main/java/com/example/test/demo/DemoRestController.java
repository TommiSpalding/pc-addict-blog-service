package com.example.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DemoRestController {

    @Autowired
    private ArticleRepository repo;

    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public ResponseEntity<Void> postArticle(@RequestBody Article a, UriComponentsBuilder b) {

        repo.saveEntity(a);

        UriComponents uriComponents = b.path("/articles/{id}").buildAndExpand(a.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Article>> getArticle() {

        Iterable<Article> articles = repo.findAll();
        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Article value : articles) { size++; }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<Iterable<Article>>(articles, status);
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Article> getArticle(@PathVariable long id) {

        Article article = repo.findOne(id);

        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {

        repo.delete(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
