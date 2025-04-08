package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleTotalStats;
import com.example.utapCattle.service.SaleService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class SaleController {

	private final SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}

	@PostMapping("/sell")
	public ResponseEntity<SaleDto> sellCattle(@RequestBody SaleDto saleDto) {
		SaleDto savedSale = saleService.sellCattle(saleDto);
		return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
	}

	@PostMapping("/getExistingSaleDates")
	public ResponseEntity<List<Sale>> getExistingSaleDates(@RequestBody String saleMarketId) {
		List<Sale> saleList = saleService.getExistingSaleDates(Long.parseLong(saleMarketId));
		return new ResponseEntity<>(saleList, HttpStatus.OK);
	}

	@PostMapping("/getSaleTotalStats")
	public ResponseEntity<SaleTotalStats> getSaleTotalStats(@RequestBody String saleId) {
		SaleTotalStats saleTotalStats = saleService.getSaleTotalStats(Long.parseLong(saleId));
		return new ResponseEntity<>(saleTotalStats, HttpStatus.OK);
	}
}