package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.mapper.CommentMapper;
import com.example.utapCattle.model.dto.CommentDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.repository.CommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final CommentMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository,
							  CommentMapper mapper) {
		this.commentRepository = commentRepository;
		this.mapper = mapper;
	}

	@Override
	public List<CommentDto> saveComments(List<Comment> comments) throws CommentException {
		validateCommentVO(comments);
		return commentRepository.saveAll(comments).stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Long getNextSequenceValue() {
		return commentRepository.getNextSequenceValue();
	}

	private void validateCommentVO(List<Comment> comments) throws CommentException {
		for (final Comment comment : comments) {
			if (StringUtils.isBlank(comment.getComment())) {
				throw new CommentException("Comment text is a mandatory field and cannot be null or empty.");
			}
			if (comment.getId() == null) {
				final Long id = getNextSequenceValue();
				comment.setId(id);
			}
		}
	}

}
