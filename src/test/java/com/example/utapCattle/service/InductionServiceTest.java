package com.example.utapCattle.service;

import com.example.utapCattle.mapper.CattleMapper;
import com.example.utapCattle.service.impl.InductionServiceImpl;
import com.example.utapCattle.service.repository.CattleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class InductionServiceTest {

    @Mock
    private CattleRepository cattleRepository;
    @Mock
    private CattleService cattleService;
    @Mock
    private TreatmentHistoryService treatmentHistoryService;
    private CattleMapper mapper = Mappers.getMapper(CattleMapper.class);
    private InductionService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new InductionServiceImpl(cattleRepository, cattleService, treatmentHistoryService);
    }

    //TODO: have to check the database and refactor as database is down now

}
