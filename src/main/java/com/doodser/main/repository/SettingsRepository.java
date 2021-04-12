package com.doodser.main.repository;

import com.doodser.main.model.GlobalSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<GlobalSetting, Integer> {
}
