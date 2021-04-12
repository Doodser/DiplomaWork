package com.doodser.main.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name = "post_comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1970, Calendar.JANUARY, 1, 0, 0, 0);

        return (this.getTime().getTime() + calendar.getTimeInMillis()) / 1000;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
