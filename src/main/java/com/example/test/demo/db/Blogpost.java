package com.example.test.demo.db;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Blogpost entity. Contains the comments of the blogpost aswell.
 */
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

    /**
     * Instantiates a new Blogpost. Sets the time to the time the blogpost was created.
     */
    public Blogpost() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    /**
     * Instantiates a new Blogpost.
     *
     * @param title      the title
     * @param textBody   the text body
     * @param authorName the author name
     */
    public Blogpost(String title, String textBody, String authorName) {

        setTitle(title);
        setTextBody(textBody);
        setAuthorName(authorName);
        setTimePosted(Instant.now().getEpochSecond());
    }

    /**
     * Gets blog id.
     *
     * @return the blog id
     */
    public long getBlogId() {
        return blogId;
    }

    /**
     * Sets blog id.
     *
     * @param id the id
     */
    public void setBlogId(long id) {
        this.blogId = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets text body.
     *
     * @return the text body
     */
    public String getTextBody() {
        return textBody;
    }

    /**
     * Sets text body.
     *
     * @param textbody the textbody
     */
    public void setTextBody(String textbody) {
        this.textBody = textbody;
    }

    /**
     * Gets author name.
     *
     * @return the author name
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets author name.
     *
     * @param authorName the author name
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Gets time posted.
     *
     * @return the time posted
     */
    public long getTimePosted() {
        return timePosted;
    }

    /**
     * Sets time posted.
     *
     * @param timePosted the time posted
     */
    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Add comment.
     *
     * @param c the c
     */
    public void addComment(Comment c) {

        comments.add(c);
    }

    /**
     * Remove comment.
     *
     * @param c the c
     */
    public void removeComment(Comment c) {

        comments.remove(c);
    }

    /**
     * Gets comment.
     *
     * @param id the id
     * @return the comment
     */
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
