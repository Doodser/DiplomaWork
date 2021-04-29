package com.doodser.main.service;

import com.doodser.main.api.response.TagsResponse;
import com.doodser.main.data.ModerationStatus;
import com.doodser.main.model.Tag;
import com.doodser.main.model.custom.TagAndPostsCount;
import com.doodser.main.repository.PostsRepository;
import com.doodser.main.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagsService {
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private PostsRepository postsRepository;

    private List<TagAndPostsCount> tagsAndPostsCounts;
    private long totalPostsCount;
    private float highestWeight;

    public TagsResponse getTags(String query) {
        Pageable pageRequest = PageRequest.of(0, 20);
        tagsAndPostsCounts = tagsRepository.getMostPopularTags(pageRequest);

        Map<Tag, Float> tagsAndWeights = new HashMap<>();

        totalPostsCount = postsRepository.countAllByIsActiveAndModerationStatus(1, ModerationStatus.ACCEPTED);
        highestWeight = getHighestWeight();

        if (query.isEmpty()) {
            tagsAndPostsCounts.forEach(t -> tagsAndWeights.put(t.getTag(), getNormalizedWeight(t)));
        } else {
            tagsAndPostsCounts.forEach(t -> {
                if (t.getTag().getName().startsWith(query)) {
                    tagsAndWeights.put(t.getTag(), getNormalizedWeight(t));
                }
            });
        }

        return new TagsResponse(tagsAndWeights);
    }

    private float getNormalizedWeight(TagAndPostsCount tag) {
        float weight = getWeight(tag.getPostsCount(), totalPostsCount);
        float normalizationCoefficient = 1 / highestWeight;

        return weight * normalizationCoefficient;
    }

    private float getWeight(long postsWithTag, long totalPostsCount) {
        return (float) postsWithTag / (float) totalPostsCount;
    }

    private float getHighestWeight() {
        return (float) tagsAndPostsCounts.get(0).getPostsCount() / (float) totalPostsCount;
    }
}