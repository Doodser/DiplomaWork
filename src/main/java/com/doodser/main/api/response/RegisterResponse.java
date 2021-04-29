package com.doodser.main.api.response;

import java.util.HashMap;

public class RegisterResponse {
    boolean result;
    HashMap<String, String> errors;

    public RegisterResponse() {
        this.result = false;
        this.errors = new HashMap<>();
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
