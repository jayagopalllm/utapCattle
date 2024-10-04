package com.example.utapCattle.service;

import com.example.utapCattle.mapper.MarketMapper;
import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.impl.MarketServiceImpl;
import com.example.utapCattle.service.repository.MarketRepository;
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

public class MarketServiceTest {

    @Mock
    private MarketRepository marketRepository;
    private MarketMapper mapper = Mappers.getMapper(MarketMapper.class);
    private MarketService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new MarketServiceImpl(marketRepository, mapper);
    }

    @Test
    public void testGetAllMarkets_WhenMarketsExist_ShouldReturnAllMarkets() {
       List<MarketDto> marketDtoList = Arrays.asList(MarketDto.builder()
               .marketId(123L)
               .marketName("MarketA")
               .current("A")
               .holdingNumber("123").build());

        List<Market> marketList = marketDtoList.stream().map(mapper::toEntity).collect(Collectors.toList());
        when(marketRepository.findAll()).thenReturn(marketList);

        List<MarketDto> result = service.getAllMarkets();
        assertEquals(result, marketDtoList);
    }


    @Test
    public void testGetMarketById_WhenMarketIdExists_ShouldReturnMarket() {
        MarketDto marketDto = MarketDto.builder()
                .marketId(123L)
                .marketName("MarketA")
                .current("A")
                .holdingNumber("123").build();

        Market market = mapper.toEntity(marketDto);

        when(marketRepository.findById(123L)).thenReturn(Optional.of(market));
        MarketDto result = service.getMarketById(123L);
        assertEquals(result, marketDto);
    }

    @Test
    public void testSaveMarket_WhenMarketIsSaved_ShouldReturnSavedMarket() {
        MarketDto marketDto = MarketDto.builder()
                .marketId(123L)
                .marketName("MarketA")
                .current("A")
                .holdingNumber("123").build();

        Market market = mapper.toEntity(marketDto);

        when(marketRepository.save(any(Market.class))).thenReturn(market);
        MarketDto result = service.saveMarket(market);
        assertEquals(result, marketDto);
    }

}
