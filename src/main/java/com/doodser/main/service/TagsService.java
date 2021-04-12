package com.doodser.main.service;

import com.doodser.main.api.response.TagsResponse;
import com.doodser.main.data.ModerationStatus;
import com.doodser.main.model.Tag;
import com.doodser.main.repository.PostsRepository;
import com.doodser.main.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TagsResponse getTags(String query) {
        List<Tag> tags = tagsRepository.findAll();
        Map<Tag, Float> tagsAndWeights = new HashMap<>();

        if (query.isEmpty()) {
            tags.forEach(tag -> tagsAndWeights.put(tag, getNormalizedWeight(tag)));
        } else {
            tags.forEach(tag -> {
                if (tag.getName().startsWith(query)) {
                    tagsAndWeights.put(tag, getNormalizedWeight(tag));
                }
            });
        }

        return new TagsResponse(tagsAndWeights);
    }

    private float getNormalizedWeight(Tag tag) {
        float weight = getWeight(tag);
        float normalizationCoefficient = 1 / getHighestWeight();

        return weight * normalizationCoefficient;
    }

    private float getWeight(Tag tag) {
        float postsCount = postsRepository.countAllByIsActiveAndModerationStatus(1, ModerationStatus.ACCEPTED);
        float postsWithTagCount = postsRepository.countAllActiveAndAcceptedPostsByTag(tag.getName());

        return postsWithTagCount / postsCount;
    }

    private float getHighestWeight() {
        List<Tag> tags = tagsRepository.findAll();
        float highestWeight = getWeight(tags.get(0));

        for (Tag tag : tags) {
            float weight = getWeight(tag);
            if (weight > highestWeight) {
                highestWeight = weight;
            }
        }

        return highestWeight;
    }
}