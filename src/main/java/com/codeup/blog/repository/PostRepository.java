package com.codeup.blog.repository;

import com.codeup.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title); // select * from post where title = ?
    Post findFirstByTitle(String title); // select * from post where title = ? limit 1

    @Query("from Post p where p.id like ?1")
    Post getAdById(long id);

    @Query("from Post p where p.title like %:term%")
    List<Post> searchByTitleLike(@Param("term") String term);
}
