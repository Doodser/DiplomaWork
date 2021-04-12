package com.doodser.main.service;

import com.doodser.main.api.response.CalendarResponse;
import com.doodser.main.data.ModerationStatus;
import com.doodser.main.model.Post;
import com.doodser.main.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarService {
    @Autowired
    private PostsRepository postsRepository;
    private List<Post> posts;
    private final Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public CalendarResponse getPostsCalendar(int year) {
        posts = postsRepository
                .findAllByIsActiveAndModerationStatusOrderByTimeAsc(1, ModerationStatus.ACCEPTED);

        HashMap<String, Integer> postsCounts = getDateToPostCountInRequiredYear(year);
        List<Integer> years = getYearsWithPosts();

        return new CalendarResponse(years, postsCounts);
    }

    private List<Integer> getYearsWithPosts() {
        ArrayList<Integer> years = new ArrayList<>();

        int postYear = 0;
        for (Post p : posts) {
            calendar.setTime(p.getTime());

            if (calendar.get(Calendar.YEAR) != postYear) {
                postYear = calendar.get(Calendar.YEAR);
                years.add(postYear);
            }
        }

        return years;
    }

    private HashMap<String, Integer> getDateToPostCountInRequiredYear(int year) {
        HashMap<String, Integer> postsCounts = new HashMap<>();

        String date;
        int count;
        for (Post p : posts) {
            calendar.setTime(p.getTime());

            if (calendar.get(Calendar.YEAR) == year) {
                date = getFormattedDate(calendar.getTime());

                if (!postsCounts.containsKey(date)) {

                    count = countPostsWithDate(date);
                    postsCounts.put(date, count);
                }
            }
        }

        return postsCounts;
    }

    private int countPostsWithDate(String date) {
        int count = 0;
        for (Post p : posts) {
            if (getFormattedDate(p.getTime()).equals(date)) count++;
        }

        return count;
    }

    private String getFormattedDate(Date date) {
        return formatter.format(date);
    }
}
