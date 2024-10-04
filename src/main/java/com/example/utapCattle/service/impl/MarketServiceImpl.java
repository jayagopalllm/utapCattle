package com.example.utapCattle.service.impl;

import com.example.utapCattle.mapper.MarketMapper;
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

	private final MarketMapper mapper;

	public MarketServiceImpl(MarketRepository marketRepository, MarketMapper mapper) {
		this.marketRepository = marketRepository;
		this.mapper = mapper;
	}

	@Override
	public List<MarketDto> getAllMarkets() {
		return marketRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	@Override
	public MarketDto getMarketById(Long id) {
		Optional<Market> market = marketRepository.findById(id);
		return market.map(mapper::toDto).orElse(null);
	}

	@Override
	public MarketDto saveMarket(Market market) {
		Market savedMarket = marketRepository.save(market);
		return mapper.toDto(savedMarket);
	}

}
