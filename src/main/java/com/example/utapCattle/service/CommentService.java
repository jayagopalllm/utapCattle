package com.example.utapCattle.service;

import java.util.List;

import com.example.utapCattle.model.entity.Comment;

public interface CommentService {

	public Long getNextSequenceValue();

	public List<Comment> saveComments(final List<Comment> comments);
}
