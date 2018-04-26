package com.example.test.demo.db;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.time.Instant;

/**
 * Comment entity.
 */
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

    /**
     * Instantiates a new Comment. Sets the time to the time the blogpost was created.
     */
    public Comment() {
        setTimePosted(Instant.now().getEpochSecond());
    }

    /**
     * Instantiates a new Comment.
     *
     * @param textbody the textbody
     * @param author   the author
     */
    public Comment(String textbody, String author) {

        setTextbody(textbody);
        setAuthor(author);
        setTimePosted(Instant.now().getEpochSecond());
    }

    /**
     * Gets comment id.
     *
     * @return the comment id
     */
    public long getCommentId() {
        return id;
    }

    /**
     * Sets comment id.
     *
     * @param id the id
     */
    public void setCommentId(long id) {
        this.id = id;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets textbody.
     *
     * @return the textbody
     */
    public String getTextbody() {
        return textbody;
    }

    /**
     * Sets textbody.
     *
     * @param textbody the textbody
     */
    public void setTextbody(String textbody) {
        this.textbody = textbody;
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
     * Gets likes.
     *
     * @return the likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets likes.
     *
     * @param likes the likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Add like.
     */
    public void addLike() { likes++; }

    /**
     * Remove like.
     */
    public void removeLike() { likes--; }
}
