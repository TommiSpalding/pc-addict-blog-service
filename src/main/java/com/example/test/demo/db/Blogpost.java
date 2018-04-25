package com.example.test.demo.db;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Blogpost extends ResourceSupport{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="blogpost_id")
    private long blogId;

    private String title;

    @Column(length = 5000)
    private String textBody;

    @Column(name="author_name")
    private String authorName;

    @Column(name="time_posted")
    private long timePosted;

    @ElementCollection
    @OneToMany(cascade=CascadeType.ALL)
    @Column(name="comments_id")
    private List<Comment> comments = new ArrayList<>();

    public Blogpost() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    public Blogpost(String title, String textBody, String authorName) {

        setTitle(title);
        setTextBody(textBody);
        setAuthorName(authorName);
        setTimePosted(Instant.now().getEpochSecond());
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long id) {
        this.blogId = id;
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

    public void setTextBody(String textbody) {
        this.textBody = textbody;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment c) {

        comments.add(c);
    }

    public void removeComment(Comment c) {

        comments.remove(c);
    }

    public Optional<Comment> getComment(int id) {

        Optional<Comment> opt = Optional.empty();

        try {
            opt = Optional.of(comments.get(id));
        } catch (IndexOutOfBoundsException ex) {

            //it will send the error message in the calling method
        }

        return opt;
    }
}
