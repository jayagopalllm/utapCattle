package com.example.utapCattle.model.mapper;

import com.example.utapCattle.model.dto.CommentDto;
import com.example.utapCattle.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);
}