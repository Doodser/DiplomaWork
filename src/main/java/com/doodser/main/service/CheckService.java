package com.doodser.main.service;

import com.doodser.main.api.response.CheckResponse;
import org.springframework.stereotype.Service;

@Service
public class CheckService {
    public CheckResponse getCheck() {
        return new CheckResponse(false);
    }
}
