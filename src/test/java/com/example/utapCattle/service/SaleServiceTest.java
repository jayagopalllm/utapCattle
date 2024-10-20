package com.example.utapCattle.service;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.mapper.SaleMapper;
import com.example.utapCattle.model.dto.CommentDto;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.dto.WeightHistoryDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.impl.SaleServiceImpl;
import com.example.utapCattle.service.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private WeightHistoryService weightHistoryService;

    private SaleMapper mapper = Mappers.getMapper(SaleMapper.class);

    private SaleService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new SaleServiceImpl(saleRepository, commentService, weightHistoryService);
    }

    @Test
    public void testSellCattle_WhenSaleAndCommentAndWeightInfoPresentInSaleDto_ShouldSaveSaleAndCommentAndWeightInformationAndReturnSavedSaleDto() throws CommentException {

        SaleDto saleDto = SaleDto.builder().
                saleId(null).
                saleDate("2024-10-05").
                saleMarketId(123L).
                cattleId(1L).
                penId(2L).
                comment("Sample Sale").
                weight(123D).build();

        when(commentService.getNextSequenceValue()).thenReturn(2L);
        when(saleRepository.getNextSequenceValue()).thenReturn(3L);
        when(commentService.saveComment(any(Comment.class))).thenReturn(new CommentDto().builder().build());
        when(weightHistoryService.saveWeightHistory(any(WeightHistory.class))).thenReturn(new WeightHistoryDto().builder().build());
        when(saleRepository.save(any(Sale.class))).thenReturn(mapper.toEntity(saleDto.builder().saleId(3L).build()));
        SaleDto result = service.sellCattle(saleDto);
        assertNotEquals(result.getSaleId(), null);
    }
}
