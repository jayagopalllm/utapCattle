package com.example.utapCattle.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.service.repository.SaleRepository;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SaleServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExistingSaleDates() {
        // Arrange
        Long marketId = 1L;
        Sale sale1 = new Sale();
        sale1.setSaleDate("2025-04-10 12:06:27");
        Sale sale2 = new Sale();
        sale2.setSaleDate("2025-04-11 15:13:16.113");
        Sale sale3 = new Sale();
        sale3.setSaleDate("2024-11-15");
        List<Sale> mockSales = Arrays.asList(sale1, sale2,sale3);

        when(saleRepository.findAllBySaleMarketIdOrderBySaleDateDesc(marketId)).thenReturn(mockSales);

        // Act
        List<Sale> result = saleService.getExistingSaleDates(marketId);

        // Assert
        assertEquals("10-Apr-2025", result.get(0).getSaleDate());
        assertEquals("11-Apr-2025", result.get(1).getSaleDate());
        assertEquals("15-Nov-2024", result.get(2).getSaleDate());
    }
}
