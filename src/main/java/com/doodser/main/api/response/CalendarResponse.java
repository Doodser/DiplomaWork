package com.doodser.main.api.response;

import java.util.HashMap;
import java.util.List;

public class CalendarResponse {
    private List<Integer> years;
    private HashMap<String, Integer> posts;

    public CalendarResponse(List<Integer> years, HashMap<String, Integer> posts) {
        this.years = years;
        this.posts = posts;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public HashMap<String, Integer> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<String, Integer> posts) {
        this.posts = posts;
    }
}
