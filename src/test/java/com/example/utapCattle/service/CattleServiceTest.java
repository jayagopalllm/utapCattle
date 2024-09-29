package com.example.utapCattle.service;

import com.example.utapCattle.exception.CattleValidationException;
import com.example.utapCattle.mapper.CattleMapper;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.service.impl.CattleServiceImpl;
import com.example.utapCattle.service.repository.CattleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CattleServiceTest {

    @Mock
    private CattleRepository cattleRepository;

    private CattleMapper mapper = Mappers.getMapper(CattleMapper.class);

    private CattleService cattleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cattleService = new CattleServiceImpl(cattleRepository, mapper);
    }

    @Test
    public void fetchAllCattle_WhenCattleExists_ShouldReturnAllCattles() {
        Cattle cattle = mapper.toEntity(new CattleDto().builder()
                .id(1L)
                .cattleId(211298L)
                .prefix("pre-")
                .earTag("1221")
                .dateOfBirth("1997-12-21")
                .motherEarTag("M-123")
                .datePurchased("2012-12-21")
                .purchasePrice(BigDecimal.valueOf(100000.00))
                .saleId(1)
                .comments("Good so far").version("1").build());
        List<Cattle> cattleList = Arrays.asList(cattle);

        when(cattleRepository.findAll()).thenReturn(cattleList);

        List<CattleDto> result = cattleService.getAllCattle();
        assertEquals(result.size(), cattleList.size());
    }

    @Test
    public void fetchCattleById_WhenCattleExists_ShouldReturnCattle() {
        Cattle cattle = mapper.toEntity(new CattleDto().builder()
                .id(1L)
                .cattleId(211298L)
                .prefix("pre-")
                .earTag("1221")
                .dateOfBirth("1997-12-21")
                .motherEarTag("M-123")
                .datePurchased("2012-12-21")
                .purchasePrice(BigDecimal.valueOf(100000.00))
                .saleId(1)
                .comments("Good so far").version("1").build());
        CattleDto cattleDto = mapper.toDto(cattle);

        when(cattleRepository.findById(1L)).thenReturn(Optional.of(cattle));

        CattleDto result = cattleService.getCattleById(1L);
        assertEquals(result.getCattleId(), cattleDto.getCattleId());
    }

    @Test
    public void fetchCattlebyEarId_WhenCattleExists_ShouldReturnCattle() {
        Cattle cattle = mapper.toEntity(new CattleDto().builder()
                .id(1L)
                .cattleId(211298L)
                .prefix("pre-")
                .earTag("1221")
                .dateOfBirth("1997-12-21")
                .motherEarTag("M-123")
                .datePurchased("2012-12-21")
                .purchasePrice(BigDecimal.valueOf(100000.00))
                .saleId(1)
                .comments("Good so far").version("1").build());
        CattleDto cattleDto = mapper.toDto(cattle);

        when(cattleRepository.findByEarTag("1221")).thenReturn(Optional.of(cattle));

        CattleDto result = cattleService.getCattleByEarTag("1221");
        assertEquals(result.getEarTag(), cattleDto.getEarTag());
    }

    @Test
    public void fetchSavedCattle_WhenCattleIsSaved_ShouldReturnSavedCattle() throws Exception {

        Cattle cattle = mapper.toEntity(new CattleDto().builder()
                .id(1L)
                .cattleId(211298L)
                .prefix("pre-")
                .earTag("1221")
                .dateOfBirth("1997-12-21")
                .motherEarTag("M-123")
                .datePurchased("2012-12-21")
                .breedId(1)
                .categoryId(1)
                .purchasePrice(BigDecimal.valueOf(100000.00))
                .saleId(1)
                .comments("Good so far").version("1").build());
        CattleDto cattleDto = mapper.toDto(cattle);

        when(cattleRepository.save(any(Cattle.class))).thenReturn(cattle);
        when(cattleRepository.getNextSequenceValue()).thenReturn(1L);

        CattleDto result = cattleService.saveCattle(cattle);
        assertEquals(result, cattleDto);
    }


    @Test
    public void testSaveCattle_WhenEatTagIdOrDateOfBirthOrCategoryIdOrBreedIdOrVersionIsNull_ShouldThrowCattleValidationException() {
        Cattle inValidCattle = mapper.toEntity(new CattleDto().builder()
                .id(1L)
                .cattleId(211298L)
                .prefix("pre-")
                .motherEarTag("M-123")
                .datePurchased("2012-12-21")
                .purchasePrice(BigDecimal.valueOf(100000.00))
                .comments("Good so far")
                .saleId(1)
                .earTag(null)
                .dateOfBirth(null)
                .categoryId(null)
                .breedId(null)
                .version(null).build());
        CattleDto cattleDto = mapper.toDto(inValidCattle);

        CattleValidationException exception = assertThrows(CattleValidationException.class
                , () -> cattleService.saveCattle(inValidCattle));
        assertEquals(String.join(",",exception.getErrors()), "CATTLE_EAR_TAG_MISSING,CATTLE_DOB_MISSING,CATTLE_BREED_ID_MISSING,CATTLE_CATEGORY_MISSING,CATTLE_VERSION_MISSING");
    }
}
