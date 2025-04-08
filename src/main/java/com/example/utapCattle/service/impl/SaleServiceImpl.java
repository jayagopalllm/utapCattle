package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleTotalStats;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.SaleService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CommentRepository;
import com.example.utapCattle.service.repository.SaleRepository;
import com.example.utapCattle.service.repository.SellerMarketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

     @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SaleRepository saleRepository;
    private final CattleRepository cattleRepository;
    private final CommentRepository commentRepository;
    private final SellerMarketRepository sellerMarketRepository;
    private final WeightHistoryService weightHistoryService;

    public SaleServiceImpl(
            SaleRepository saleRepository,
            CattleRepository cattleRepository,
            CommentRepository commentRepository,
            SellerMarketRepository sellerMarketRepository,
            WeightHistoryService weightHistoryService) {
        this.saleRepository = saleRepository;
        this.cattleRepository = cattleRepository;
        this.commentRepository = commentRepository;
        this.sellerMarketRepository = sellerMarketRepository;
        this.weightHistoryService = weightHistoryService;
    }

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
        if(saleDto.getSaleId() != null) {
            sale.setSaleId(saleDto.getSaleId());
        } else {
            sale.setSaleId(saleRepository.getNextSequenceValue());
        }
        
        sale.setSaleDate(saleDto.getSaleDate());
        sale.setSaleMarketId(saleDto.getSaleMarketId());

        final Sale savedSale = saleRepository.save(sale);
        cattle.setWeightAtSale(saleDto.getWeight());
        cattle.setSaleId(savedSale.getSaleId());
        cattle  =cattleRepository.save(cattle);
       String name = sellerMarketRepository.findById(sale.getSaleMarketId()).get().getSellerMarketName();
        // Prepare response DTO
        final SaleDto responseDto = new SaleDto();
        responseDto.setSaleId(savedSale.getSaleId());
        responseDto.setSaleDate(savedSale.getSaleDate());
        responseDto.setSaleMarketId(savedSale.getSaleMarketId());
        responseDto.setSaleMarketName(name);
        responseDto.setWeight(cattle.getWeightAtSale());

        responseDto.setCattleId(cattle.getCattleId());
        responseDto.setPenId(saleDto.getPenId());

        return responseDto;
    }

    @Override
    public List<Sale> getExistingSaleDates(Long saleMarketId) {
        return saleRepository.findAllBySaleMarketId(saleMarketId);        
    }
    
    @Override
    public Sale getSaleBySaleId(Long saleId) {
        return saleRepository.findBySaleId(saleId);        
    }

    @SuppressWarnings("deprecation")
    @Override
    public SaleTotalStats getSaleTotalStats(Long saleId) {
        String query = "SELECT "+
                        "    COUNT(eartag) AS totalCattle, "+
                        "    sum(case when EXTRACT(MONTH FROM AGE(TO_DATE(c.dateofbirth, 'DD/MM/YYYY'), CURRENT_DATE::date)) + "+
                        "    EXTRACT(YEAR FROM AGE(TO_DATE(c.dateofbirth, 'DD/MM/YYYY'), CURRENT_DATE::date)) * 12 >= 30 then 1 else 0 end )AS totalOTM, "+
                        "    SUM(weightatsale) as totalWeight "+
                        "FROM "+
                        "    cattle c "+
                        "WHERE "+
                        "    saleid = ? ";

        return jdbcTemplate.queryForObject(query, new Object[]{saleId}, (rs, rowNum) -> {
            SaleTotalStats stats = new SaleTotalStats();
            stats.setTotalCattle(rs.getInt("totalCattle"));
            stats.setTotalWeight(rs.getInt("totalWeight"));
            stats.setTotalOTM(rs.getInt("totalOTM"));
            return stats;
        });
    
    }

    /**
     * Returns the current date formatted as a string in the format "yyyy-MM-dd".
     *
     * @return The formatted current date.
     */
//    private static String getCurrentFormattedDate() {
//        final Date currentDate = new Date();
//        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        return formatter.format(currentDate);
//    }
    private String getCurrentFormattedDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }   
}