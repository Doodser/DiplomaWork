package com.doodser.main.service;

import com.doodser.main.api.response.PostsResponse;
import com.doodser.main.data.ModerationStatus;
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

        return new PostsResponse(
                posts.getContent(),
                postsRepository.countAllByIsActiveAndModerationStatus(1, ModerationStatus.ACCEPTED)
        );
    }

    public PostsResponse getPostsByQuery(int offset, int limit, String query) {
        Page<Post> posts =
                postsRepository.findAllActiveAndAcceptedPostsByTitleContainingOrderByTimeDesc(
                        query, createPageRequest(offset, limit));

        return new PostsResponse(
                posts.getContent(),
                postsRepository.countAllActiveAndAcceptedPostsByTitleContaining(query)
        );
    }

    public PostsResponse getPostsByDate(int offset, int limit, Timestamp date) {
        Timestamp datePlusDay = getDateIncreasedByDay(date);

        Page<Post> posts  =
                postsRepository.findAllActiveAndAcceptedPostsByDateOrderByTimeDesc(
                        date, datePlusDay, createPageRequest(offset, limit));

        return new PostsResponse(
                posts.getContent(),
                postsRepository.countAllActiveAndAcceptedPostsByDateBetween(date, datePlusDay)
        );
    }

    public PostsResponse getPostsByTag(int offset, int limit, String tag) {
        Page<Post> posts =
                postsRepository.findAllActiveAndAcceptedPostsByTagOrderByTimeDesc(
                        tag, createPageRequest(offset, limit));

        return new PostsResponse(
                posts.getContent(),
                postsRepository.countAllActiveAndAcceptedPostsByTag(tag)
        );
    }

    public PostsResponse.PostObject getPost(int id) {
        return new PostsResponse.PostObject(postsRepository.findPostById(id));
    }

    private PageRequest createPageRequest(int offset, int limit) {
        int amountOfElements = limit - offset;
        int pageNum = offset / amountOfElements;
        return PageRequest.of(pageNum, amountOfElements);
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
