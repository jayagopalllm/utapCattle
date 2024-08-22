package com.example.utapCattle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utapCattle.model.dto.InductionDto;
import com.example.utapCattle.model.entity.Induction;
import com.example.utapCattle.service.InductionService;

@RestController
@CrossOrigin
@RequestMapping("/induction")
public class InductionController extends BaseController {

	@Autowired
	private InductionService inductionService;

	@PostMapping("/save")
	public ResponseEntity<InductionDto> saveInduction(@RequestBody final Induction induction) {
		logger.info("Incoming request: Saving induction information");
		final InductionDto savedCattleDto = inductionService.saveInduction(induction);
		logger.info("Request successful: saved induction - {}", induction.getId());
		return new ResponseEntity<>(savedCattleDto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InductionDto> getInductionById(@PathVariable final Long id) {
		logger.info("Incoming request: Get Induction information by Id");
		final InductionDto inductionDto = inductionService.getInductionById(id);
		logger.info("Request successful: Retreived induction information");
		return (inductionDto != null) ? ResponseEntity.ok(inductionDto) : ResponseEntity.noContent().build();
	}

	@GetMapping
	public List<InductionDto> getAllInductions() {
		return inductionService.getAllInductions();
	}

}
