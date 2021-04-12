package com.doodser.main.repository;

import com.doodser.main.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Integer> {

}
