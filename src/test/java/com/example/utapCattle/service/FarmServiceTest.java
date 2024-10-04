package com.example.utapCattle.service;

import com.example.utapCattle.mapper.FarmMapper;
import com.example.utapCattle.model.dto.FarmDto;
import com.example.utapCattle.model.entity.Farm;
import com.example.utapCattle.service.impl.FarmServiceImpl;
import com.example.utapCattle.service.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;
    private FarmMapper mapper = Mappers.getMapper(FarmMapper.class);
    private FarmService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new FarmServiceImpl(farmRepository, mapper);
    }

    @Test
    public void testGetAllFarms_WhenFarmExists_ShouldReturnFarms() {
        List<Farm> farmList = Arrays.asList(mapper.toEntity(new FarmDto().builder().build())
                ,mapper.toEntity(new FarmDto().builder().build()));
        List<FarmDto> farmDtoList = farmList.stream().map(mapper::toDto).collect(Collectors.toList());

        when(farmRepository.findAll()).thenReturn(farmList);
        List<FarmDto> result = service.getAllFarms();
        assertEquals(result, farmDtoList);
    }

    @Test
    public void testGetFarmById_WhenFarmExists_ThenReturnFarm() {
        FarmDto farmDto = new FarmDto().builder().farmId(1L).build();
        Farm farm = mapper.toEntity(farmDto);

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));
        FarmDto result = service.getFarmById(1L);
        assertEquals(result, farmDto);
    }

    @Test
    public void testSaveFarm_WhenFarmIsSaved_ShouldReturnSavedFarm() {
        FarmDto farmDto = new FarmDto().builder().farmId(1L).build();
        Farm farm = mapper.toEntity(farmDto);

        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        FarmDto result = service.saveFarm(farm);
        assertEquals(result, farmDto);
    }
}
