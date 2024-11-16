package com.example.utapCattle.service.impl;

import com.example.utapCattle.exception.CommentException;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.CommentService;
import com.example.utapCattle.service.SaleService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

	private final SaleRepository saleRepository;
	private final CommentService commentService;
	private final WeightHistoryService weightHistoryService;

	public SaleServiceImpl(SaleRepository saleRepository,
						   CommentService commentService,
					WeightHistoryService weightHistoryService) {
		this.saleRepository = saleRepository;
		this.commentService = commentService;
		this.weightHistoryService = weightHistoryService;
	}

	@Override
	public SaleDto sellCattle(SaleDto saleDto) throws CommentException {
		if (saleDto.getComment() != null) {
			final Long commentId = commentService.getNextSequenceValue();
			final Comment comment = new Comment();
			comment.setProcessId(new Random().nextLong());
			comment.setId(commentId);
			comment.setCattleId(saleDto.getCattleId());
			comment.setComments(saleDto.getComment());
			comment.setCommentDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			commentService.saveComment(comment);
		}
		if (saleDto.getWeight() != null) {
			final WeightHistory weightHistory = new WeightHistory();
			weightHistory.setCattleId(saleDto.getCattleId());
			weightHistory.setWeightDateTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			weightHistory.setWeight(saleDto.getWeight());
			weightHistoryService.saveWeightHistory(weightHistory);
		}
		final Sale sale = new Sale();
		sale.setSaleId(saleRepository.getNextSequenceValue());
		sale.setSaleDate(saleDto.getSaleDate());
		sale.setSaleMarketId(saleDto.getSaleMarketId());
		final Sale savedSale = saleRepository.save(sale);

		return new SaleDto().builder().saleId(savedSale.getSaleId()).
				saleDate(savedSale.getSaleDate()).
				saleMarketId(savedSale.getSaleMarketId()).
				cattleId(saleDto.getCattleId()).
				penId(saleDto.getPenId()).build();
	}
}