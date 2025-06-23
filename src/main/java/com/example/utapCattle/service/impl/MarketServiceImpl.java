package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.MarketDto;
import com.example.utapCattle.model.entity.Market;
import com.example.utapCattle.model.entity.SellerMarket;
import com.example.utapCattle.model.entity.SlaughterMarket;
import com.example.utapCattle.service.MarketService;
import com.example.utapCattle.service.repository.MarketRepository;
import com.example.utapCattle.service.repository.SellerMarketRepository;
import com.example.utapCattle.service.repository.SlaughterMarketRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketServiceImpl implements MarketService {

	private final MarketRepository marketRepository;
	private final SellerMarketRepository sellerMarketRepository;
	private final SlaughterMarketRepository slaughterMarketRepository;

	public MarketServiceImpl(MarketRepository marketRepository, SellerMarketRepository sellerMarketRepository, SlaughterMarketRepository slaughterMarketRepository) {
		this.marketRepository = marketRepository;
		this.sellerMarketRepository = sellerMarketRepository;
		this.slaughterMarketRepository = slaughterMarketRepository;
	}

	@Override
	public List<MarketDto> getAllMarkets(Long userFarmId) {
		return marketRepository.findByUserFarmIdOrderByMarketNameAsc(userFarmId).stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<MarketDto> findAllMarketsByUserId(Long userId) {
		return marketRepository.findByUserFarmIdOrderByMarketNameAsc(userId).stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public MarketDto getMarketById(Long id) {
		Optional<Market> market = marketRepository.findById(id);
		return market.map(this::mapToDto).orElse(null);
	}

	@Override
	@Transactional
	public MarketDto saveMarket(Market market) {
		Market savedMarket = marketRepository.save(market);

		SellerMarket sellerMarket = mapToSellerMarketEntity(savedMarket);
		sellerMarketRepository.save(sellerMarket);

		SlaughterMarket slaughterMarket = mapToSlaughterMarketEntity(savedMarket);
		slaughterMarketRepository.save(slaughterMarket);

		return mapToDto(savedMarket);
	}

	// Helper method to map Market to MarketDto
	private MarketDto mapToDto(Market market) {
		return new MarketDto(market.getMarketId(), null, market.getMarketName(),
				market.getHoldingNumber(),
				market.getCurrent());
	}

	/**
	 * Converts Market/MarketDto to SellerMarket entity
	 */
	private SellerMarket mapToSellerMarketEntity(Market market) {
		SellerMarket sellerMarket = new SellerMarket();

		// Map direct fields
		sellerMarket.setSellerMarketName(market.getMarketName());
		sellerMarket.setSellerHoldingNumber(market.getHoldingNumber());
		sellerMarket.setSellerCurrent(market.getCurrent());
		sellerMarket.setUserFarmId(market.getUserFarmId());

		// If copying ID from main Market entity (added after Market is saved)
		if (market.getMarketId() != null) {
			sellerMarket.setSellerMarketId(market.getMarketId());
		}

		return sellerMarket;
	}

	/**
	 * Converts Market/MarketDto to SlaughterMarket entity
	 */
	private SlaughterMarket mapToSlaughterMarketEntity(Market market) {
		SlaughterMarket slaughterMarket = new SlaughterMarket();

		// Map direct fields
		slaughterMarket.setMarketName(market.getMarketName());
		slaughterMarket.setHoldingNumber(market.getHoldingNumber());
		slaughterMarket.setCurrent(market.getCurrent());
		slaughterMarket.setUserFarmId(market.getUserFarmId());

		// If copying ID from main Market entity (with type conversion)
		if (market.getMarketId() != null) {
			slaughterMarket.setMarketId(market.getMarketId().intValue());
		}

		return slaughterMarket;
	}

	@Override
	@Transactional
	public MarketDto update(Long id, Market updatedMarket) {
		return marketRepository.findById(id)
				.map(existingMarket -> {
					// Update main Market entity
					updateMarketEntity(existingMarket, updatedMarket);
					Market savedMarket = marketRepository.save(existingMarket);

					// Synchronize with SellerMarket
					sellerMarketRepository.findById(id)
							.ifPresent(sellerMarket -> {
								updateSellerMarketEntity(sellerMarket, updatedMarket);
								sellerMarketRepository.save(sellerMarket);
							});

					// Synchronize with SlaughterMarket
					slaughterMarketRepository.findById(id)
							.ifPresent(slaughterMarket -> {
								updateSlaughterMarketEntity(slaughterMarket, updatedMarket);
								slaughterMarketRepository.save(slaughterMarket);
							});

					return mapToDto(savedMarket);
				})
				.orElseThrow(() -> new EntityNotFoundException("Market not found with id: " + id));
	}


	// Helper method to update Market entity
	private void updateMarketEntity(Market target, Market source) {
		target.setMarketName(source.getMarketName());
		target.setHoldingNumber(source.getHoldingNumber());
		target.setCurrent(source.getCurrent());
	}

	// Helper method to update SellerMarket entity
	private void updateSellerMarketEntity(SellerMarket target, Market source) {
		target.setSellerMarketName(source.getMarketName());
		target.setSellerHoldingNumber(source.getHoldingNumber());
		target.setSellerCurrent(source.getCurrent());
	}

	// Helper method to update SlaughterMarket entity
	private void updateSlaughterMarketEntity(SlaughterMarket target, Market source) {
		target.setMarketName(source.getMarketName());
		target.setHoldingNumber(source.getHoldingNumber());
		target.setCurrent(source.getCurrent());
	}

	@Override
	@Transactional
	public void delete(Long marketId) {

		if (!marketRepository.existsById(marketId)) {
			throw new EntityNotFoundException("Market record not found with id: " + marketId);
		}

		if (sellerMarketRepository.existsById(marketId)) {
			sellerMarketRepository.deleteById(marketId);
		}

		if (slaughterMarketRepository.existsById(marketId)) {
			slaughterMarketRepository.deleteById(marketId);
		}

		marketRepository.deleteById(marketId);
	}
}
