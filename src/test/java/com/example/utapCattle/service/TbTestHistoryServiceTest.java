package com.example.utapCattle.service;

import com.example.utapCattle.mapper.TbTestHistoryMapper;
import com.example.utapCattle.model.dto.TbTestHistoryDto;
import com.example.utapCattle.model.entity.TbTestHistory;
import com.example.utapCattle.service.impl.TbTestHistoryServiceImpl;
import com.example.utapCattle.service.repository.TbTestHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TbTestHistoryServiceTest {

    @Mock
    private TbTestHistoryRepository tbTestHistoryRepository;
    @Mock
    private CattleService cattleService;
    private TbTestHistoryMapper mapper = Mappers.getMapper(TbTestHistoryMapper.class);
    private TbTestHistoryService service;
    List<String> validationErrors = new ArrayList<>();

    @BeforeEach()
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new TbTestHistoryServiceImpl(tbTestHistoryRepository, cattleService, mapper);
    }

    @Test
    public void testSaveTbTestHistory_WhenTestHistoryDataIsSaved_ShouldReturnSavedTbTestHistory() throws Exception {
        TbTestHistoryDto tbTestHistoryDto = TbTestHistoryDto.builder().build();

        when(tbTestHistoryRepository.getNextSequenceValue()).thenReturn(1L);
        when(tbTestHistoryRepository.save(any(TbTestHistory.class))).thenReturn(mapper.toEntity(tbTestHistoryDto));

        TbTestHistoryDto result = service.saveTbTestHistory(tbTestHistoryDto);
        assertEquals(result, tbTestHistoryDto);
    }
}
