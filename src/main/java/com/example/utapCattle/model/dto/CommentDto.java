package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String comment;
    private Long processId;
    private Long cattleId;
    private Long userId;
    private Long entityId;
    private String commentDate;
}
