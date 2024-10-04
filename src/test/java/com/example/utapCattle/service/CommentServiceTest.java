package com.example.utapCattle.service;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.mapper.CommentMapper;
import com.example.utapCattle.model.dto.CommentDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.service.impl.CommentServiceImpl;
import com.example.utapCattle.service.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    private CommentService commentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, mapper);
    }

    @Test
    public void testSaveComments_WhenCommentsAreSaved_ShouldReturnSavedComments() throws CommentException {
        List<CommentDto> commentDtoList = Arrays.asList(
                 new CommentDto().builder().id(1L).comment("Comment1").build()
                ,new CommentDto().builder().id(2L).comment("Comment2").build());
        List<Comment> commentList = commentDtoList
                .stream().map(mapper::toEntity).collect(Collectors.toList());

        when(commentRepository.saveAll(commentList)).thenReturn(commentList);
        when(commentRepository.getNextSequenceValue()).thenReturn(1L).thenReturn(2L);
        List<CommentDto> result = commentService.saveComments(commentList);
        assertEquals(result, commentDtoList);
    }

    //TODO: should the cattleId and EntityId and ProcessId should also be checked for null ?
    @Test
    public void testSaveComments_WhenCommentIsNull_ShouldThrowCommentValidationException() {
        List<CommentDto> commentDtoList = Arrays.asList(
                new CommentDto().builder().id(1L).comment(null).build()
                ,new CommentDto().builder().id(2L).comment(null).build());
        List<Comment> commentList = commentDtoList
                .stream().map(mapper::toEntity).collect(Collectors.toList());

        CommentException result = assertThrows(CommentException.class
                , () -> commentService.saveComments(commentList));
        assertEquals(result.getError(), "Comment text is a mandatory field and cannot be null or empty.");
    }
}
