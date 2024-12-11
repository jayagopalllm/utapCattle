package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.entity.SlaughterHouse;
import com.example.utapCattle.service.CattleService;
import com.example.utapCattle.service.SlaughterService;
import com.example.utapCattle.service.repository.SlaughterRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlaughterServiceImpl implements SlaughterService {

    private final SlaughterRepository slaughterRepository;
    private final CattleService cattleService;

    public SlaughterServiceImpl(SlaughterRepository slaughterRepository, CattleService cattleService) {
        this.slaughterRepository = slaughterRepository;
        this.cattleService = cattleService;
    }


    public Long saveSlaughterData(Long id, MultipartFile file) {
        try {
            List<SlaughterHouse> slaughterDataList = parseCsvFile(id, file);

            List<SlaughterHouse> savedData = slaughterRepository.saveAll(slaughterDataList);

            return (long) savedData.size();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save slaughter data", e);
        }
    }

    private List<SlaughterHouse> parseCsvFile(Long id, MultipartFile file) throws Exception {
        List<SlaughterHouse> slaughterDataList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy"); // Ensure this matches the CSV format

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            // Iterate through each record in the CSV file
            for (CSVRecord record : csvParser) {
                SlaughterHouse data = new SlaughterHouse();

                String earTag1 = record.get(0);
                // Get the earTag1 value from the CSV
                CattleDto cattleDto = cattleService.getCattleByEarTag(earTag1);
                Long cattleId = cattleDto != null ? cattleDto.getCattleId() : null;

                data.setCattleId(cattleId);
                data.setSlaughterMarket(id);
                data.setEarTag1(earTag1);
                data.setEarTag1(record.get(0)); // Eartag1 is at index 0
                data.setCarcassNumber(parseInteger(record.get(1))); // CarcassNumber is at index 1
                data.setSide1Weight(parseBigDecimal(record.get(2))); // Side1Weight is at index 2
                data.setSide2Weight(parseBigDecimal(record.get(3))); // Side2Weight is at index 3
                data.setHotWeight(parseBigDecimal(record.get(4))); // HotWeight is at index 4
                data.setReb(parseBigDecimal(record.get(5))); // REB is at index 5
                data.setColdWeight(parseBigDecimal(record.get(6))); // ColdWeight is at index 6
                data.setKillDate(parseDate(record.get(7), dateFormatter)); // Kill_Date is at index 7
                data.setFarmAssured(record.get(8)); // Farm_Assured is at index 8
                data.setGrade(record.get(9)); // Grade is at index 9
                data.setCategory(record.get(10)); // Category is at index 10
                data.setBreed(record.get(11)); // Breed is at index 11
                data.setDateOfBirth(parseDate(record.get(12), dateFormatter)); // DOB is at index 12
                data.setSide1Label(parseInteger(record.get(13))); // Side1_Label is at index 13
                data.setSide2Label(parseInteger(record.get(14))); // Side2_Label is at index 14
                data.setDestination(record.get(15)); // Destination is at index 15
                data.setBand(record.get(16)); // Band is at index 16
                data.setTextBox50(parseBigDecimal(record.get(17))); // Textbox50 is at index 17
                data.setTextBox51(parseBigDecimal(record.get(18))); // Textbox51 is at index 18
                data.setTextBox52(parseBigDecimal(record.get(19))); // Textbox52 is at index 19
                data.setTextBox53(parseBigDecimal(record.get(20))); // Textbox53 is at index 20
                data.setTextBox54(parseBigDecimal(record.get(21))); // Textbox54 is at index 21

                // Add the entity to the list
                slaughterDataList.add(data);
            }
        }

        return slaughterDataList;
    }


    private Integer parseInteger(String value) {
        try {
            return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return value != null && !value.isEmpty() ? new BigDecimal(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private LocalDate parseDate(String value, DateTimeFormatter formatter) {
        try {
            return value != null && !value.isEmpty() ? LocalDate.parse(value, formatter) : null;
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + value); // Log the error
            return null;
        }
    }
}
