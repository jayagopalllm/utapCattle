package com.example.utapCattle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.repository.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketRepository marketRepository;

	@Override
	public List<MarketDto> getAllMarkets() {
		return marketRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public MarketDto getMarketById(Long id) {
		Optional<Market> market = marketRepository.findById(id);
		return market.map(this::mapToDto).orElse(null);
	}

	@Override
	public MarketDto saveMarket(Market market) {
		Market savedMarket = marketRepository.save(market);
		return mapToDto(savedMarket);
	}

	// Helper method to map Market to MarketDto
	private MarketDto mapToDto(Market market) {
		return new MarketDto(market.getMarketId(), market.getMarketName(), market.getHoldingNumber(),
				market.getCurrent());
	}
}
