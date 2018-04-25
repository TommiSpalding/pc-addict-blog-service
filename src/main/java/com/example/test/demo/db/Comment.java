package com.example.test.demo.db;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Comment extends ResourceSupport {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="comment_id")
    private long id;

    private int likes;

    private String author;

    @Column(length = 5000)
    private String textbody;

    @Column(name="time_posted")
    private long timePosted;

    //@ManyToOne
    //@JoinColumn(name="blogpost_id")
    //private Blogpost blogpost;

    public Comment() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    public Comment(String textbody, String author) {

        setTextbody(textbody);
        setAuthor(author);
        setTimePosted(Instant.now().getEpochSecond());
    }

    /*
    public Comment(Blogpost blogpost,String textbody, String author) {

        setTextbody(textbody);
        setAuthor(author);
        setBlogpost(blogpost);
        setTimePosted(Instant.now().getEpochSecond());
    }*/

    public long getCommentId() {
        return id;
    }

    public void setCommentId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTextbody() {
        return textbody;
    }

    public void setTextbody(String textbody) {
        this.textbody = textbody;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    //public Blogpost getBlogpost() {
    //    return blogpost;
    //}

    //public void setBlogpost(Blogpost blogpost) {
    //    this.blogpost = blogpost;
    //}


    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void addLike() { likes++; }
    public void removeLike() { likes--; }
}
