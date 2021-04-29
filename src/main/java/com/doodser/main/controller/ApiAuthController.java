package com.doodser.main.controller;

import com.doodser.main.api.request.RegisterRequest;
import com.doodser.main.api.response.CaptchaResponse;
import com.doodser.main.api.response.CheckResponse;
import com.doodser.main.api.response.RegisterResponse;
import com.doodser.main.service.CaptchaService;
import com.doodser.main.service.CheckService;
import com.doodser.main.service.RegisterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final CheckService checkService;
    private final CaptchaService captchaService;
    private final RegisterService registerService;

    public ApiAuthController(CheckService checkService, CaptchaService captchaService, RegisterService registerService) {
        this.checkService = checkService;
        this.captchaService = captchaService;
        this.registerService = registerService;
    }

    @GetMapping("/check")
    private CheckResponse getCheck() {
        return checkService.getCheck();
    }

    @GetMapping("/captcha")
    private CaptchaResponse getCaptcha() {
        return captchaService.getCaptcha();
    }

    @PostMapping("/register")
    private RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return  registerService.register(registerRequest);
    }
}