package com.example.utapCattle.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbTestHistoryDto {

    private Long tbTestHistoryId;
    private Long cattleId;
    private Long userId1;
    private Long userId2;
    private LocalDateTime testDate;
    private Integer measA1;
    private Integer measB1;
    private Integer measA2;
    private Integer measB2;
    private String reactionDescA;
    private String reactionDescB;
    private String overallResult;
    private String remarks;
    private String earTag;
}
