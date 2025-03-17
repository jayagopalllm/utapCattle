package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.repository.MarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketServiceImpl implements MarketService {

	private final MarketRepository marketRepository;

	public MarketServiceImpl(MarketRepository marketRepository) {
		this.marketRepository = marketRepository;
	}

	@Override
	public List<MarketDto> getAllMarkets() {
		return marketRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public List<MarketDto> findAllMarketsByUserId(Long userId) {
		return marketRepository.findAllMarketsByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
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
		return new MarketDto(market.getMarketId(),market.getMarketId()
				, market.getMarketName(), market.getHoldingNumber(),
				market.getCurrent());
	}
}
