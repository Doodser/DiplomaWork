package com.doodser.main.repository;

import com.doodser.main.data.ModerationStatus;
import com.doodser.main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {

    int countAllByModerationStatusEquals(ModerationStatus moderationStatus);

    int countAllByIsActiveAndModerationStatus(int isActive, ModerationStatus moderationStatus);

    @Query("select count(p.id) from Post p where p.title like ?1 " +
            "and p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    int countAllActiveAndAcceptedPostsByTitleContaining(String title);

    @Query("select count(p.id) from Post p where p.time > ?1 and p.time < ?2 " +
            "and p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    int countAllActiveAndAcceptedPostsByDateBetween(Timestamp date, Timestamp datePlusDay);

    @Query(value = "select count(*) from posts p where p.is_active = 1 and p.moderation_status = 'ACCEPTED' " +
            "and (select count(*) from tag2post t2p where" +
            " t2p.tag_id = (select t.id from tags t where t.name = ?1) and t2p.post_id = p.id)" +
            " > 0",
            nativeQuery = true)
    int countAllActiveAndAcceptedPostsByTag(String tag);

    @Query("select p from Post p where p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllActiveAndAcceptedPostsOrderByTimeDesc(Pageable pageable);

    @Query("select p from Post p where p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllActiveAndAcceptedPostsOrderByTimeAsc(Pageable pageable);

    @Query("select p from Post p where p.isActive = 1 and p.moderationStatus = 'ACCEPTED' order by p.comments.size desc")
    Page<Post> findAllActiveAndAcceptedPostsOrderByCommentsCount(Pageable pageable);

    @Query(value = "select * from posts p where p.is_active = 1 and p.moderation_status = 'ACCEPTED' " +
            "order by (select count(*) from post_votes where value = 1 and post_id = p.id) desc",
            nativeQuery = true)
    Page<Post> findAllActiveAndAcceptedPostsOrderByLikes(Pageable pageable);

    @Query("select p from Post p where p.title like ?1 " +
            "and p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllActiveAndAcceptedPostsByTitleContainingOrderByTimeDesc(String title, Pageable pageable);

    @Query("select p from Post p where p.time > ?1 and p.time < ?2 " +
            "and p.isActive = 1 and p.moderationStatus = 'ACCEPTED'")
    Page<Post> findAllActiveAndAcceptedPostsByDateOrderByTimeDesc(Timestamp time, Timestamp timePlusDay, Pageable pageable);

    @Query(value = "select * from posts p where p.is_active = 1 and p.moderation_status = 'ACCEPTED'" +
            " and (select count(*) from tag2post t2p where" +
            " t2p.tag_id = (select t.id from tags t where t.name = ?1) and t2p.post_id = p.id)" +
            " > 0",
            nativeQuery = true)
    Page<Post> findAllActiveAndAcceptedPostsByTagOrderByTimeDesc(String tag, Pageable pageable);

    Post findPostById(int id);

    List<Post> findAllByIsActiveAndModerationStatusOrderByTimeAsc(int isActive, ModerationStatus moderationStatus);
}
