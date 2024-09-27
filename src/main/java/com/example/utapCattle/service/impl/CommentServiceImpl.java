package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.repository.CommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public List<Comment> saveComments(List<Comment> comments) {
		validateCommentVO(comments);
		commentRepository.saveAll(comments);
		return null;
	}

	@Override
	public Long getNextSequenceValue() {
		return commentRepository.getNextSequenceValue();
	}

	private void validateCommentVO(List<Comment> comments) {
		for (final Comment comment : comments) {
			if (StringUtils.isBlank(comment.getComment())) {
				throw new IllegalArgumentException("Comment Text is mandatory field.");
			}
			if (comment.getId() == null) {
				final Long id = getNextSequenceValue();
				comment.setId(id);
			}
		}
	}

}
