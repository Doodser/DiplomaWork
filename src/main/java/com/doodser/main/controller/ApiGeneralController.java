package com.doodser.main.controller;

import com.doodser.main.api.response.CalendarResponse;
import com.doodser.main.api.response.InitResponse;
import com.doodser.main.api.response.SettingsResponse;
import com.doodser.main.api.response.TagsResponse;
import com.doodser.main.service.CalendarService;
import com.doodser.main.service.SettingsService;
import com.doodser.main.service.TagsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private final TagsService tagsService;
    private final SettingsService settingsService;
    private final CalendarService calendarService;
    private final InitResponse initResponse;

    public ApiGeneralController(TagsService tagsService, SettingsService settingsService,
                                CalendarService calendarService, InitResponse initResponse) {
        this.tagsService = tagsService;
        this.settingsService = settingsService;
        this.calendarService = calendarService;
        this.initResponse = initResponse;
    }

    @GetMapping("/init")
    private InitResponse getInit() {
        return initResponse;
    }

    @GetMapping("/settings")
    private SettingsResponse getSettings() {
        return settingsService.getSettings();
    }

    @GetMapping("/tag")
    private TagsResponse getTags(@RequestParam(defaultValue = "") String query) {
        return tagsService.getTags(query);
    }

    @GetMapping("/calendar")
    private CalendarResponse getCalendar(@RequestParam(defaultValue = "") String year) {
        if (year.isBlank()) {
            int currentYear = getCurrentYear();
            return calendarService.getPostsCalendar(currentYear);
        }
        return calendarService.getPostsCalendar(Integer.parseInt(year));
    }

    private int getCurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }
}
