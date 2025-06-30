package com.example.utapCattle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.utapCattle.model.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT nextval('comments_seq')", nativeQuery = true)
	Long getNextSequenceValue();

	@Query("SELECT c FROM Comment c WHERE (c.comment IS NOT NULL AND c.comment <> '') AND c.cattleId = :cattleId order by c.commentDate desc")
    List<Comment> findValidCommentsByCattleId(@Param("cattleId") Long cattleId);
}
