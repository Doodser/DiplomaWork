package com.doodser.main.api.response;

import com.doodser.main.model.Comment;
import com.doodser.main.model.Post;
import com.doodser.main.model.User;
import com.doodser.main.view.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

public class PostsResponse {
    @JsonView(Views.PostsRequestData.class)
    private long count;

    @JsonView(Views.PostsRequestData.class)
    private List<PostObject> posts = new ArrayList<>();

    @JsonView(Views.PostRequestFullData.class)
    private PostObject post;

    public PostsResponse(List<Post> posts, long count) {
        posts.forEach(post -> this.posts.add(new PostObject(post)));
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<PostObject> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostObject> posts) {
        this.posts = posts;
    }

    public PostObject getPost() {
        return post;
    }

    public void setPost(PostObject post) {
        this.post = post;
    }

    public static class PostObject {
        @JsonView(Views.PostsRequestMainData.class)
        private int id;
        @JsonView(Views.PostsRequestMainData.class)
        private long timestamp;
        @JsonView(Views.PostsRequestMainData.class)
        private User user;
        @JsonView(Views.PostsRequestMainData.class)
        private String title;
        @JsonView(Views.PostsRequestMainData.class)
        private String announce;
        @JsonView(Views.PostsRequestMainData.class)
        private int likeCount;
        @JsonView(Views.PostsRequestMainData.class)
        private int dislikeCount;
        @JsonView(Views.PostsRequestMainData.class)
        private int viewCount;
        @JsonView(Views.PostsRequestData.class)
        private int commentCount;

        @JsonView(Views.PostRequestFullData.class)
        private boolean active;
        @JsonView(Views.PostRequestFullData.class)
        private List<CommentObject> comments;
        @JsonView(Views.PostRequestFullData.class)
        private List<String> tags;
        @JsonView(Views.PostRequestFullData.class)
        private String text;

        private static class CommentObject {
            @JsonView(Views.CommentData.class)
            int id;
            @JsonView(Views.CommentData.class)
            long timestamp;
            @JsonView(Views.CommentData.class)
            String text;
            @JsonView(Views.CommentData.class)
            User user;
            public CommentObject(Comment comment) {
                id = comment.getId();
                timestamp = comment.getTimestamp();
                text = comment.getText();
                user = comment.getUser();
            }
        }
        public PostObject(Post post) {
            id = post.getId();
            timestamp = post.getTimestamp();
            active = post.getIsActive() == 1;
            user = post.getUser();
            title = post.getTitle();
            announce = post.getAnnounce();
            likeCount = post.countVoteByValue(1);
            dislikeCount = post.countVoteByValue(-1);
            commentCount = post.countComments();
            comments = new ArrayList<>();
            post.getComments().forEach(comment -> comments.add(new CommentObject(comment)));
            viewCount = post.getViewCount();
            tags = new ArrayList<>();
            post.getTags().forEach(tag -> tags.add(tag.getName()));
            text = post.getText();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnnounce() {
            return announce;
        }

        public void setAnnounce(String announce) {
            this.announce = announce;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getDislikeCount() {
            return dislikeCount;
        }

        public void setDislikeCount(int dislikeCount) {
            this.dislikeCount = dislikeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public List<CommentObject> getComments() {
            return comments;
        }

        public void setComments(List<CommentObject> comments) {
            this.comments = comments;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

