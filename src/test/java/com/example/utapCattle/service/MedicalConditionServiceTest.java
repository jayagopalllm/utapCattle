package com.example.utapCattle.service;

import com.example.utapCattle.mapper.MedicalConditionMapper;
import com.example.utapCattle.model.dto.MedicalConditionDto;
import com.example.utapCattle.model.entity.MedicalCondition;
import com.example.utapCattle.service.impl.MedicalConditionServiceImpl;
import com.example.utapCattle.service.repository.MedicalConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MedicalConditionServiceTest {

    @Mock
    private MedicalConditionRepository medicalConditionRepository;

    private MedicalConditionMapper mapper = Mappers.getMapper(MedicalConditionMapper.class);

    private MedicalConditionService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new MedicalConditionServiceImpl(medicalConditionRepository, mapper);
    }

    @Test
    public void testGetAllMedicalCondition_WhenMedicalConditionExists_ShouldReturnMedicalConditionList() {
        List<MedicalConditionDto> medicalConditionDtoList = Arrays.asList(new MedicalConditionDto().builder().build(),
                new MedicalConditionDto().builder().build());

        List<MedicalCondition> medicalConditions = medicalConditionDtoList.stream().map(mapper::toEntity).collect(Collectors.toList());

        when(medicalConditionRepository.findAll()).thenReturn(medicalConditions);
        List<MedicalConditionDto> result = service.getAllMedicalCondition();
        assertEquals(result, medicalConditionDtoList);
    }
}
