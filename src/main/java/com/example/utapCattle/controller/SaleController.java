package com.example.utapCattle.controller;

import com.example.utapCattle.model.dto.SaleDateRequest;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleTotalStats;
import com.example.utapCattle.service.SaleService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@CrossOrigin(origins = { "http://localhost:4200", "http://35.178.210.158" })
public class SaleController extends BaseController {

	private final SaleService saleService;

	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}

	@PostMapping("/sell")
	public ResponseEntity<SaleDto> sellCattle(@RequestBody SaleDto saleDto, HttpServletRequest request) {
		Long userId = Long.parseLong(request.getHeader("User-ID"));
		SaleDto savedSale = saleService.sellCattle(saleDto, userId);
		return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
	}

	@PostMapping("/keep")
	public ResponseEntity<SaleDto> keepCattle(@RequestBody SaleDto saleDto, HttpServletRequest request) {
		Long userId = Long.parseLong(request.getHeader("User-ID"));
		SaleDto savedSale = saleService.keepCattle(saleDto, userId);
		return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
	}

	@GetMapping("/{saleMarketId}/existing-sale-dates")
	public ResponseEntity<Object> getExistingSaleDates(@PathVariable String saleMarketId) {
		try {
			List<Sale> saleList = saleService.getExistingSaleDates(Long.parseLong(saleMarketId));
			return new ResponseEntity<>(saleList, HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("Invalid saleMarketId format: {}", saleMarketId, e);
			return new ResponseEntity<>("Invalid saleMarketId.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{saleId}/total-stats")
	public ResponseEntity<Object> getSaleTotalStats(@PathVariable String saleId) {
		try {
			SaleTotalStats saleTotalStats = saleService.getSaleTotalStats(Long.parseLong(saleId));
			return new ResponseEntity<>(saleTotalStats, HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("Invalid saleId format: {}", saleId, e);
			return new ResponseEntity<>("Invalid saleId.", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/validate-date")
	public ResponseEntity<Boolean> checkForValidSaleDate(@RequestBody SaleDateRequest request) {
		boolean isvalidSaleDate = saleService.checkForValidSaleDate(request);
		return new ResponseEntity<>(isvalidSaleDate, HttpStatus.OK);
	}

	@GetMapping("/{saleId}/cattle-info")
	public ResponseEntity<List<CattleDto>> getAllCattleBySaleId(@PathVariable String saleId) {
		List<CattleDto> cattList = saleService.getAllCattleBySaleId(Long.parseLong(saleId));
		return new ResponseEntity<>(cattList, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSale(@PathVariable String id) {
		try {
			saleService.deleteSale(Long.parseLong(id));
			return new ResponseEntity<>("Sale deleted successfully.", HttpStatus.OK);
		} catch (NumberFormatException e) {
			logger.error("Invalid saleId format: {}", id, e);
			return new ResponseEntity<>("Invalid saleId.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Error deleting sale with id: {}", id, e);
			return new ResponseEntity<>("Error deleting sale.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
