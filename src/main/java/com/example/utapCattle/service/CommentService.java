package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.exception.CommentValidationException;
import com.example.utapCattle.model.dto.CommentDto;
import com.example.utapCattle.model.entity.Comment;

public interface CommentService {

	public Long getNextSequenceValue();

	public List<CommentDto> saveComments(final List<Comment> comments) throws CommentValidationException;
}
