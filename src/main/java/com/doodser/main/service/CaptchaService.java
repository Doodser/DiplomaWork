package com.doodser.main.service;

import com.doodser.main.api.response.CaptchaResponse;
import com.doodser.main.model.CaptchaCode;
import com.doodser.main.repository.CaptchaRepository;
import com.github.cage.Cage;
import com.github.cage.GCage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class CaptchaService {
    @Autowired
    private CaptchaRepository captchaRepository;
    private final Cage cage = new GCage();

    public CaptchaResponse getCaptcha() {
        String code = cage.getTokenGenerator().next();
        String secretCode = cage.getTokenGenerator().next();
        byte[] captchaBytes = cage.draw(code);

        captchaRepository.save(new CaptchaCode(code, secretCode));

        BufferedImage captcha;
        try {
            captcha = ImageIO.read(new ByteArrayInputStream(captchaBytes));
            captcha = resizeCaptcha(captcha);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(captcha, "png", baos);
            captchaBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CaptchaResponse(secretCode, Base64.getEncoder().encodeToString(captchaBytes));
    }

    private BufferedImage resizeCaptcha(BufferedImage captcha) {
        double scaleX = (double) 100 / (double) captcha.getWidth();
        double scaleY = (double) 35 / (double) captcha.getHeight();

        AffineTransform transform =
                AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage resizedImage = new BufferedImage(100, 35, captcha.getType());
        op.filter(captcha, resizedImage);
        return resizedImage;
    }

    @Scheduled(fixedDelay = 450000)
    private void deleteDeprecatedCodes() {
        Date today = new Date();
        List<CaptchaCode> codes = captchaRepository.findAll();

        captchaRepository.deleteAll(
                codes.stream().filter(code -> (today.getTime() - code.getTime().getTime() >= 3600000))
                .collect(Collectors.toList()));
    }
}