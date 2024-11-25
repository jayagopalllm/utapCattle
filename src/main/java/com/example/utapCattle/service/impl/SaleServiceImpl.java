package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.SaleService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CommentRepository;
import com.example.utapCattle.service.repository.SaleRepository;
import com.example.utapCattle.service.repository.SellerMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CattleRepository cattleRepository;

    @Autowired
    private CommentRepository commentRepository;
@Autowired
    private SellerMarketRepository sellerMarketRepository;

    @Autowired
    private WeightHistoryService weightHistoryService;

    @Override
    public SaleDto sellCattle(SaleDto saleDto) {

        Cattle cattle = cattleRepository.findByCattleId(saleDto.getCattleId())
                .orElseThrow(() -> new RuntimeException("Cattle not found"));
        // Persist comment if present
        if (saleDto.getComment() != null) {
            final Comment comment = new Comment();
            final Long commentId = commentRepository.getNextSequenceValue();
            comment.setProcessId(4L);
            comment.setId(commentId);
            comment.setCattleId(saleDto.getCattleId());
            comment.setComment(saleDto.getComment());
            comment.setCommentDate(getCurrentFormattedDate());
            commentRepository.save(comment);
        }

        // Persist weight history if present
        if (saleDto.getWeight() != null) {
            final WeightHistory weightHistory = new WeightHistory();
            weightHistory.setCattleId(saleDto.getCattleId());
            weightHistory.setWeightDateTime(getCurrentFormattedDate());
            weightHistory.setWeight(saleDto.getWeight());
            weightHistoryService.saveWeightHistory(weightHistory);
        }
        final Sale sale = new Sale();
        sale.setSaleId(saleRepository.getNextSequenceValue());
        sale.setSaleDate(saleDto.getSaleDate());
        sale.setSaleMarketId(saleDto.getSaleMarketId());

        final Sale savedSale = saleRepository.save(sale);
        cattle.setSaleId(savedSale.getSaleId().intValue());
        cattle  =cattleRepository.save(cattle);
       String name = sellerMarketRepository.findById(sale.getSaleMarketId()).get().getSellerMarketName();
        // Prepare response DTO
        final SaleDto responseDto = new SaleDto();
        responseDto.setSaleId(savedSale.getSaleId());
        responseDto.setSaleDate(savedSale.getSaleDate());
        responseDto.setSaleMarketId(savedSale.getSaleMarketId());
        responseDto.setSaleMarketName(name);

        responseDto.setCattleId(cattle.getCattleId());
        responseDto.setPenId(saleDto.getPenId());

        return responseDto;
    }

    /**
     * Returns the current date formatted as a string in the format "yyyy-MM-dd".
     *
     * @return The formatted current date.
     */
    private static String getCurrentFormattedDate() {
        final Date currentDate = new Date();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentDate);
    }
}