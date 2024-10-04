package com.example.utapCattle.service;

import com.example.utapCattle.mapper.MedicationMapper;
import com.example.utapCattle.model.dto.MedicationDto;
import com.example.utapCattle.model.entity.Medication;
import com.example.utapCattle.service.impl.MedicationServiceImpl;
import com.example.utapCattle.service.repository.MedicationRepository;
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

public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    private final MedicationMapper mapper = Mappers.getMapper(MedicationMapper.class);

    private MedicationService service;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new MedicationServiceImpl(medicationRepository, mapper);
    }

    @Test
    public void testGetAllMedication_WhenMedicationExists_ShouldReturnMedicationList() {
        List<MedicationDto> medicationDtoList = Arrays.asList(new MedicationDto().builder().build(),
                new MedicationDto().builder().build());

        List<Medication> medications = medicationDtoList.stream().map(mapper::toEntity).collect(Collectors.toList());
        when(medicationRepository.findAll()).thenReturn(medications);
        List<MedicationDto> result = service.getAllMedication();
        assertEquals(result, medicationDtoList);
    }

}
