package com.doodser.main.api.response;

import com.doodser.main.model.User;
import com.doodser.main.repository.PostsRepository;

public class CheckResponse {
    private boolean result;
    private CheckUserObject user;

    public CheckResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    private static class CheckUserObject {
        private int id;
        private String name;
        private String photo;
        private String email;
        private boolean moderation;
        private int moderationCount;
        private boolean settings = true;

        public CheckUserObject(User user) {
            id = user.getId();
            name = user.getName();
            photo = user.getPhoto();
            email = user.getEmail();
            moderation = (user.getIsModerator() == 1);
            if (moderation) {
                moderationCount = 0;
            } else {
                moderationCount = 0;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isModeration() {
            return moderation;
        }

        public void setModeration(boolean moderation) {
            this.moderation = moderation;
        }

        public int getModerationCount() {
            return moderationCount;
        }

        public void setModerationCount(int moderationCount) {
            this.moderationCount = moderationCount;
        }

        public boolean isSettings() {
            return settings;
        }

        public void setSettings(boolean settings) {
            this.settings = settings;
        }
    }
}
