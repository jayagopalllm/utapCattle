package com.example.utapCattle.service;

import com.example.utapCattle.mapper.MovementMapper;
import com.example.utapCattle.service.impl.MovementServiceImpl;
import com.example.utapCattle.service.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;

    private MovementMapper mapper = Mappers.getMapper(MovementMapper.class);

    private MovementService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new MovementServiceImpl(movementRepository, mapper);
    }
}
