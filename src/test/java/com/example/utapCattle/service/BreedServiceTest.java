package com.example.utapCattle.service;

import com.example.utapCattle.mapper.BreedMapper;
import com.example.utapCattle.model.dto.BreedDto;
import com.example.utapCattle.model.entity.Breed;
import com.example.utapCattle.service.impl.BreedServiceImpl;
import com.example.utapCattle.service.repository.BreedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BreedServiceTest {

    @Mock
    private BreedRepository breedRepository;
    private BreedMapper breedMapper = Mappers.getMapper(BreedMapper.class);
    private BreedService breedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        breedService = new BreedServiceImpl(breedRepository, breedMapper);
    }

    @Test
    public void testGetAllBreeds_WhenBreedExists_ShouldReturnAllBreed() {
        Breed breed = breedMapper.toEntity(new BreedDto().builder().breedid(1L)
                .breeddesc("Sample-Breed")
                .breedabbr("ABC")
                .breedfull("ABCD")
                .breedcatego("AC")
                .beefdairy("Dairy1")
                .build());
        List<Breed> breedList = Arrays.asList(breed);

        when(breedRepository.findAll()).thenReturn(breedList);

        List<BreedDto> result = breedService.getAllBreeds();
        assertEquals(result.size(), breedList.size());
    }

    @Test
    public void testGetBreedById_WhenBreedExists_ShouldReturnBreed() {
        Breed breed = breedMapper.toEntity(new BreedDto().builder().breedid(1L)
                .breeddesc("Sample-Breed")
                .breedabbr("ABC")
                .breedfull("ABCD")
                .breedcatego("AC")
                .beefdairy("Dairy1")
                .build());
        BreedDto breedDto = breedMapper.toDto(breed);

        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));

        BreedDto result = breedService.getBreedById(1L);
        assertEquals(result, breedDto);
    }

    @Test
    public void testSaveBreed_WhenBreedIsSaved_ShouldReturnSavedBreed() {
        Breed breed = breedMapper.toEntity(new BreedDto().builder().breedid(1L)
                .breeddesc("Sample-Breed")
                .breedabbr("ABC")
                .breedfull("ABCD")
                .breedcatego("AC")
                .beefdairy("Dairy1")
                .build());
        BreedDto breedDto = breedMapper.toDto(breed);

        when(breedRepository.save(any(Breed.class))).thenReturn(breed);

        BreedDto result = breedService.saveBreed(breed);
        assertEquals(result, breedDto);
    }
}
