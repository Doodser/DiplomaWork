package com.doodser.main.model.custom;

import com.doodser.main.model.Tag;

public class TagAndPostsCount {
    private Tag tag;
    private long postsCount;

    public TagAndPostsCount(Tag tag, long postsCount) {
        this.tag = tag;
        this.postsCount = postsCount;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public long getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(long postsCount) {
        this.postsCount = postsCount;
    }
}
