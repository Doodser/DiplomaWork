package com.doodser.main.api.response;

import com.doodser.main.model.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TagsResponse {
    private List<TagObject> tags;

    public TagsResponse(Map<Tag, Float> tagsAndWeight) {
        tags = new ArrayList<>();
        tagsAndWeight.forEach((tag, weight) ->
            tags.add(new TagObject(tag.getName(), weight)));
    }

    public List<TagObject> getTags() {
        return tags;
    }

    public void setTags(List<TagObject> tags) {
        this.tags = tags;
    }

    private static class TagObject {
        private String name;
        private float weight;

        public TagObject(String name, float weight) {
            this.name = name;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }
    }
}
