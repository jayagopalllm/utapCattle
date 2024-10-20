package com.example.utapCattle.service;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.exception.SaleException;
import com.example.utapCattle.model.dto.SaleDto;

public interface SaleService {
    SaleDto sellCattle(final SaleDto saleDto) throws CommentException;
}