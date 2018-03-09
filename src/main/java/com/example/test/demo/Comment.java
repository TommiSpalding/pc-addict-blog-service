package com.example.test.demo;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Comment {
    @Id @GeneratedValue
    private long id;
    private String author;
    private String textbody;
    @Column(name="time_posted")
    private long timePosted;
    @ManyToOne
    @JoinColumn(name="blogpost_id")
    private Blogpost blogpost;

    public Comment() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    public Comment(Blogpost blogpost,String textbody, String author) {
        setTextbody(textbody);
        setAuthor(author);
        setBlogpost(blogpost);
        setTimePosted(Instant.now().getEpochSecond());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Blogpost getBlogpost() {
        return blogpost;
    }

    public void setBlogpost(Blogpost blogpost) {
        this.blogpost = blogpost;
    }
}
