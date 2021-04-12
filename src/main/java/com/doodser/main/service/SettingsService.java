package com.doodser.main.service;

import com.doodser.main.api.response.SettingsResponse;
import com.doodser.main.model.GlobalSetting;
import com.doodser.main.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepository repository;

    public SettingsResponse getSettings() {
        List<GlobalSetting> settings = repository.findAll();
        boolean multiuserMode = false;
        boolean postPremoderation = false;
        boolean statisticIsPublic = false;

        for (GlobalSetting setting : settings) {
            switch (setting.getCode()) {
                case "MULTIUSER_MODE":
                    multiuserMode = setting.getValue().equals("YES");
                    break;
                case "POST_PREMODERATION":
                    postPremoderation = setting.getValue().equals("YES");
                    break;
                case "STATISTIC_IS_PUBLIC":
                    statisticIsPublic = setting.getValue().equals("YES");
                    break;
            }

        }

        return new SettingsResponse(multiuserMode, postPremoderation, statisticIsPublic);
    }
}
