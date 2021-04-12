package com.doodser.main.service;

import com.doodser.main.api.response.CaptchaResponse;
import com.doodser.main.model.CaptchaCode;
import com.doodser.main.repository.CaptchaRepository;
import com.github.cage.Cage;
import com.github.cage.YCage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CaptchaService {
    @Autowired
    private CaptchaRepository captchaRepository;
    private final Cage cage = new YCage();

    public CaptchaResponse getCaptcha() {
        deleteDeprecatedCodes();

        String code = cage.getTokenGenerator().next();
        String secretCode = cage.getTokenGenerator().next();

        captchaRepository.save(new CaptchaCode(code, secretCode));

        return new CaptchaResponse(secretCode, Base64.getEncoder().encodeToString(code.getBytes()));
    }

    private void deleteDeprecatedCodes() {
        Date today = new Date();
        List<CaptchaCode> codes = captchaRepository.findAll();

        captchaRepository.deleteAll(
                codes.stream().filter(code -> (today.getTime() - code.getTime().getTime() >= 3600000))
                .collect(Collectors.toList()));
    }
}