package com.doodser.main.service;

import com.doodser.main.api.response.PostsResponse;
import com.doodser.main.model.Post;
import com.doodser.main.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    public PostsResponse getPosts(int offset, int limit, String mode) {
        Page<Post> posts = getPageByMode(mode, createPageRequest(offset, limit));

        return getPostsResponse(posts);
    }

    public PostsResponse getPostsByQuery(int offset, int limit, String query) {
        Page<Post> posts =
                postsRepository.findAllActiveAndAcceptedPostsByTitleContainingOrderByTimeDesc(
                        query, createPageRequest(offset, limit));

        return getPostsResponse(posts);
    }

    public PostsResponse getPostsByDate(int offset, int limit, Timestamp date) {
        Timestamp datePlusDay = getDateIncreasedByDay(date);

        Page<Post> posts  =
                postsRepository.findAllActiveAndAcceptedPostsByDateOrderByTimeDesc(
                        date, datePlusDay, createPageRequest(offset, limit));

        return getPostsResponse(posts);
    }

    public PostsResponse getPostsByTag(int offset, int limit, String tag) {
        Page<Post> posts =
                postsRepository.findAllActiveAndAcceptedPostsByTagOrderByTimeDesc(
                        tag, createPageRequest(offset, limit));

        return getPostsResponse(posts);
    }

    public PostsResponse.PostObject getPost(int id) {
        Post post = postsRepository.findPostById(id);
        incrementViews(post);
        postsRepository.save(post);

        return new PostsResponse.PostObject(post);
    }

    private PostsResponse getPostsResponse(Page<Post> posts) {
        return new PostsResponse(
                posts.getContent(),
                posts.getTotalElements()
        );
    }

    private void incrementViews(Post post) {
        post.setViewCount(post.getViewCount() + 1);
    }

    private PageRequest createPageRequest(int offset, int limit) {
        int pageNum = offset / limit;
        return PageRequest.of(pageNum, limit);
    }

    private Timestamp getDateIncreasedByDay(Timestamp date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.add(Calendar.DATE, 1);

        return new Timestamp(calendar.getTime().getTime());
    }

    private Page<Post> getPageByMode(String mode, Pageable pageRequest) {
        switch (mode) {
            case "recent" :
                return postsRepository.findAllActiveAndAcceptedPostsOrderByTimeDesc(pageRequest);
            case "early" :
                return postsRepository.findAllActiveAndAcceptedPostsOrderByTimeAsc(pageRequest);
            case "popular" :
                return postsRepository.findAllActiveAndAcceptedPostsOrderByCommentsCount(pageRequest);
            case "best" :
                return postsRepository.findAllActiveAndAcceptedPostsOrderByLikes(pageRequest);
            default: throw new IllegalArgumentException("Wrong mode type!");
        }
    }
}
