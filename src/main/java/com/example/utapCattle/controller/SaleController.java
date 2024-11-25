package com.example.utapCattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.service.SaleService;

@RestController
@RequestMapping("/sale")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class SaleController {

	@Autowired
	private SaleService saleService;

	@PostMapping("/sell")
	public ResponseEntity<SaleDto> sellCattle(@RequestBody SaleDto saleDto) {
		SaleDto savedSale = saleService.sellCattle(saleDto);
		return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
	}
}