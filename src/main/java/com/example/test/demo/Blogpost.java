package com.example.test.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class Blogpost {
    @Id @GeneratedValue
    private long id;
    private String title;
    @Column(name="textbody")
    private String textBody;
    @Column(name="author_name")
    private String authorName;
    @Column(name="post_date")
    private long postDate;

    public Blogpost() {
        setPostDate(Instant.now().getEpochSecond());
    }

    public Blogpost(String title, String textBody, String authorName) {
        setTitle(title);
        setTextBody(textBody);
        setAuthorName(authorName);
        setPostDate(Instant.now().getEpochSecond());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getPostDate() {
        return postDate;
    }

    public void setPostDate(long postDate) {
        this.postDate = postDate;
    }
}
