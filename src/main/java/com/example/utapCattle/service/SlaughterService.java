package com.example.utapCattle.service;

import com.example.utapCattle.model.dto.AgentDto;
import com.example.utapCattle.model.entity.Agent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SlaughterService {

    Long saveSlaughterData(Long name , MultipartFile file);
}
