package com.example.test.demo.controller;

import com.example.test.demo.db.Blogpost;
import com.example.test.demo.db.BlogpostRepository;
import com.example.test.demo.db.Comment;
import com.example.test.demo.db.CommentRepository;
import com.example.test.demo.exception.CannotFindBlogpostException;
import com.example.test.demo.exception.CannotFindCommentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private BlogpostRepository repo;

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<Void> postBlogpost(@RequestBody Blogpost a, UriComponentsBuilder b) {

        repo.save(a);

        UriComponents uriComponents = b.path("/blogposts/{id}").buildAndExpand(a.getBlogId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Blogpost>> getBlogposts() {

        Iterable<Blogpost> blogposts = repo.findAll();
        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Blogpost value : blogposts) {
            addHATEOAStoBlogpost(value);
            size++;
        }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(blogposts, status);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blogpost> getBlogpost(@PathVariable long id) throws CannotFindBlogpostException {

        Optional<Blogpost> opt = repo.findById(id);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(id);

        Blogpost blogpost = opt.get();
        addHATEOAStoBlogpost(blogpost);

        return new ResponseEntity<>(blogpost, HttpStatus.OK);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBlogpost(@PathVariable long id) throws CannotFindBlogpostException {

        if (!repo.findById(id).isPresent()) throw new CannotFindBlogpostException(id);

        repo.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/blogposts/search", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Blogpost>> search(@RequestParam(value = "q", required = false) String search) {

        Iterable<Blogpost> blogposts = null;

        if(search == null || search == "")
            return new ResponseEntity<>(blogposts, HttpStatus.NOT_FOUND);

        blogposts = repo.findByTextBodyContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCase(search, search, search);

        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Blogpost value : blogposts) {
            addHATEOAStoBlogpost(value);
            size++;
        }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(blogposts, status);
    }

    @RequestMapping(value = "/blogposts/searchAuthor", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Blogpost>> searchAuthor(@RequestParam(value = "q", required = false) String search) {

        Iterable<Blogpost> blogposts = null;

        if(search == null || search == "")
            return new ResponseEntity<>(blogposts, HttpStatus.NOT_FOUND);

        blogposts = repo.findByAuthorNameContainingIgnoreCase(search);

        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Blogpost value : blogposts) {
            addHATEOAStoBlogpost(value);
            size++;
        }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(blogposts, status);
    }

    //#############################################Comments###################################################


    //is this needed????????????? yes is!!!!!!!!!!!!!!!!!
    @Autowired
    private CommentRepository cRepo;

    @RequestMapping(value = "blogposts/{blogpostsId}/comments", method = RequestMethod.POST)
    public ResponseEntity<Void> postComment(@PathVariable long blogpostsId, @RequestBody Comment a, UriComponentsBuilder b) throws CannotFindBlogpostException {

        Optional<Blogpost> opt = repo.findById(blogpostsId);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(blogpostsId);

        Blogpost blogpost = opt.get();

        blogpost.addComment(a);
        repo.save(blogpost);

        UriComponents uriComponents = b.path("/{blogpostsId}/comments/{id}").buildAndExpand(blogpostsId, a.getCommentId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "blogposts/{blogpostsId}/comments", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Comment>> getComments(@PathVariable long blogpostsId) throws CannotFindBlogpostException {

        Optional<Blogpost> opt = repo.findById(blogpostsId);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(blogpostsId);

        Blogpost blogpost = opt.get();

        Iterable<Comment> comments = blogpost.getComments();
        HttpStatus status = HttpStatus.OK;

        int size = 0;
        for(Comment value : comments) {
            addHATEOAStoComment(value, blogpost, size);
            size++;
        }

        if(size == 0)
            status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(comments, status);
    }

    @RequestMapping(value = "blogposts/{blogpostsId}/comments/{id}", method = RequestMethod.GET)
    public ResponseEntity<Comment> getComment(@PathVariable long blogpostsId, @PathVariable int id) throws CannotFindBlogpostException, CannotFindCommentException {

        Optional<Blogpost> opt = repo.findById(blogpostsId);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(blogpostsId);

        Blogpost blogpost = opt.get();

        Optional<Comment> c = blogpost.getComment(id);

        if (!c.isPresent()) throw new CannotFindCommentException(id);

        Comment comment = c.get();
        addHATEOAStoComment(comment, blogpost, id);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @RequestMapping(value = "blogposts/{blogpostsId}/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable long blogpostsId, @PathVariable int id) throws CannotFindBlogpostException, CannotFindCommentException {

        Optional<Blogpost> opt = repo.findById(blogpostsId);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(blogpostsId);

        Blogpost blogpost = opt.get();

        Optional<Comment> c = blogpost.getComment(id);

        if (!c.isPresent()) throw new CannotFindCommentException(id);

        Comment comment = c.get();

        blogpost.removeComment(comment);

        repo.save(blogpost);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "blogposts/{blogpostsId}/comments/{id}/like", method = RequestMethod.POST)
    public ResponseEntity<Void> postLike(@PathVariable long blogpostsId, @PathVariable int id, @RequestBody String str/*, UriComponentsBuilder b*/) throws CannotFindBlogpostException, CannotFindCommentException {

        Optional<Blogpost> opt = repo.findById(blogpostsId);

        if (!opt.isPresent()) throw new CannotFindBlogpostException(blogpostsId);

        Blogpost blogpost = opt.get();

        Optional<Comment> c = blogpost.getComment(id);

        if (!c.isPresent()) throw new CannotFindCommentException(id);

        Comment comment = c.get();

        if(str.equals("yes"))
            comment.addLike();
        else if(str.equals("no"))
            comment.removeLike();

        repo.save(blogpost);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Adds HATEOAS to a blogpost.
     *
     * @param blogPost blogpost to add to
     */
    public void addHATEOAStoBlogpost(Blogpost blogPost) {
        if (blogPost == null) return;

        Link selfLink = ControllerLinkBuilder.linkTo(RestController.class).slash("blogposts").slash(blogPost.getBlogId()).withSelfRel();
        blogPost.add(selfLink);

        Link searchLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RestController.class)
                .search("keyword")).withRel("search");
        blogPost.add(searchLink);

        if (blogPost.getComments().size() > 0) {
            Link commentsLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RestController.class)
                    .getComments(blogPost.getBlogId())).withRel("allComments");
            blogPost.add(commentsLink);

            for (int i = 0; i < blogPost.getComments().size(); i++) {
                addHATEOAStoComment(blogPost.getComments().get(i), blogPost, i);
            }
        }
    }

    /**
     * Adds HATEOAS to a comment.
     *
     * @param comment comment to add to
     */
    public void addHATEOAStoComment(Comment comment, Blogpost parent, int id) {
        if (comment == null || parent == null) return;
        Link selfLink = ControllerLinkBuilder.linkTo(RestController.class)
                .slash("blogposts").slash(parent.getBlogId()).slash("comments").slash(id).withSelfRel();
        comment.add(selfLink);

        Link likeLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RestController.class)
                .postLike(parent.getBlogId(), id, "yes/no")).withRel("like");
        comment.add(likeLink);
    }
}
