package com.example.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
public class BlogpostRestController {

    @Autowired
    private BlogpostRepository repo;

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<Void> postBlogpost(@RequestBody Blogpost a, UriComponentsBuilder b) {

        repo.save(a);

        UriComponents uriComponents = b.path("/blogposts/{id}").buildAndExpand(a.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Blogpost>> getBlogposts() {

        Iterable<Blogpost> blogposts = repo.findAll();
        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Blogpost value : blogposts) { size++; }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(blogposts, status);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blogpost> getBlogpost(@PathVariable long id) throws CannotFindBlogpostException {

        Optional<Blogpost> opt = repo.findById(id);
        if (!opt.isPresent()) throw new CannotFindBlogpostException(id);
        Blogpost blogpost = opt.get();
        return new ResponseEntity<>(blogpost, HttpStatus.OK);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBlogpost(@PathVariable long id) throws CannotFindBlogpostException {
        if (!repo.findById(id).isPresent()) throw new CannotFindBlogpostException(id);
        repo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
