package com.doodser.main.repository;

import com.doodser.main.model.Tag;
import com.doodser.main.model.custom.TagAndPostsCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {
    @Query("select new com.doodser.main.model.custom.TagAndPostsCount(t, count(t)) from Tag t " +
            "join Tag2Post t2p on t.id = t2p.tagId " +
            "join Post p on t2p.postId = p.id and p.isActive = 1 and p.moderationStatus = 'ACCEPTED' " +
            "group by t.id order by count(t) desc")
    List<TagAndPostsCount> getMostPopularTags(Pageable pageable);
}
