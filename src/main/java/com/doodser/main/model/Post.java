package com.doodser.main.model;

import com.doodser.main.data.ModerationStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_active", nullable = false)
    private int isActive;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, columnDefinition = "enum")
    private ModerationStatus moderationStatus;

    @ManyToOne()
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Timestamp time;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Vote> votes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int countComments() {
        return comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public int countVoteByValue(int voteValue) {
        int count = 0;
        for (Vote vote : votes) {
            if (vote.getValue() == voteValue) {
                count++;
            }
        }

        return count;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public long getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1970, Calendar.JANUARY, 1, 0, 0, 0);

        return (this.getTime().getTime() + calendar.getTimeInMillis()) / 1000;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnnounce() {
        return text.substring(0, text.indexOf(" "));
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}

