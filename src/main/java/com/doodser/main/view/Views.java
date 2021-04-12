package com.doodser.main.view;

public class Views {
    public interface UserIdName {}
    public interface UserIdNamePhoto extends UserIdName {}
    public interface CommentData extends UserIdNamePhoto {}
    public interface PostsRequestMainData extends UserIdName {}
    public interface PostsRequestData extends PostsRequestMainData {}
    public interface PostRequestFullData extends PostsRequestMainData, CommentData {}
}
