package com.example.test.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Blogpost {
    @Id @GeneratedValue
    @Column(name="blogpost_id")
    private long blogId;
    private String title;
    private String textbody;
    @Column(name="author_name")
    private String authorName;
    @Column(name="time_posted")
    private long timePosted;

    public Blogpost() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    public Blogpost(String title, String textbody, String authorName) {
        setTitle(title);
        setTextbody(textbody);
        setAuthorName(authorName);
        setTimePosted(Instant.now().getEpochSecond());
    }

    public long getId() {
        return blogId;
    }

    public void setId(long id) {
        this.blogId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextbody() {
        return textbody;
    }

    public void setTextbody(String textbody) {
        this.textbody = textbody;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }
}
