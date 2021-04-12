package com.doodser.main.controller;

import com.doodser.main.service.PostsService;
import com.doodser.main.api.response.PostsResponse;
import com.doodser.main.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {
    private final PostsService postsService;

    public ApiPostController(PostsService postsService) {
        this.postsService = postsService;
    }

    @JsonView(Views.PostsRequestData.class)
    @GetMapping()
    private PostsResponse getPosts(int offset, int limit, String  mode) {
        return postsService.getPosts(offset, limit, mode);
    }

    @JsonView(Views.PostsRequestData.class)
    @GetMapping("/search")
    private PostsResponse getPostsByQuery(int offset, int limit, @RequestParam(defaultValue = "") String query) {
        if (query.isBlank()) {
            return postsService.getPosts(offset, limit, "recent");
        }
        return postsService.getPostsByQuery(offset, limit, "%" + query + "%");
    }

    @JsonView(Views.PostsRequestData.class)
    @GetMapping("/byDate")
    private PostsResponse getPostsByDate(int offset, int limit, String date) {
        return postsService.getPostsByDate(offset, limit, Timestamp.valueOf(date + " 00:00:00"));
    }

    @JsonView(Views.PostsRequestData.class)
    @GetMapping("/byTag")
    private PostsResponse getPostsByTag(int offset, int limit, String tag) {
        return postsService.getPostsByTag(offset, limit, tag);
    }

    @JsonView(Views.PostRequestFullData.class)
    @GetMapping("/{id}")
    private PostsResponse.PostObject getPost(@PathVariable String id) {
        return postsService.getPost(Integer.parseInt(id));
    }
}
