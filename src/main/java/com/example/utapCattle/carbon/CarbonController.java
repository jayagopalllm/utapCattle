package com.example.utapCattle.carbon;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carbon")
public class CarbonController {

    CarbonService service;
    public CarbonController(CarbonService service) {
        this.service = service;
    }

    @GetMapping("/animal-group")
    public ResponseEntity<List<AnimalGroupDto>> getAnimalGroupInfo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam String holdingNo
    ) {
        List<AnimalGroupDto> stats = service.getAnimalGroupInfo(startDate, endDate, holdingNo);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/herd-info")
    public ResponseEntity<HerdInfoDto> getHerdInfo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam String holdingNo
    ) {
        HerdInfoDto herdInfo = service.getHerdInfo(startDate, endDate, holdingNo);
        return ResponseEntity.ok(herdInfo);
    }

    @GetMapping("/feed-info")
    public ResponseEntity<Map<String, Map<String, Double>>> getFeedInfo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam String holdingNo
    ) {
        Map<String, Map<String, Double>> feedInfo = service.getFeedInfo(startDate, endDate, holdingNo);
        return ResponseEntity.ok(feedInfo);
    }

}
