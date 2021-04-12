package com.doodser.main.repository;

import com.doodser.main.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {
}
