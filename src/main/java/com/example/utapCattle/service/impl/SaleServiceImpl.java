package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.dto.CattleDto;
import com.example.utapCattle.model.dto.SaleDateRequest;
import com.example.utapCattle.model.dto.SaleDto;
import com.example.utapCattle.model.entity.Cattle;
import com.example.utapCattle.model.entity.Comment;
import com.example.utapCattle.model.entity.Movement;
import com.example.utapCattle.model.entity.Sale;
import com.example.utapCattle.model.entity.SaleTotalStats;
import com.example.utapCattle.model.entity.WeightHistory;
import com.example.utapCattle.service.MovementService;
import com.example.utapCattle.service.SaleService;
import com.example.utapCattle.service.WeightHistoryService;
import com.example.utapCattle.service.repository.CattleRepository;
import com.example.utapCattle.service.repository.CommentRepository;
import com.example.utapCattle.service.repository.SaleRepository;
import com.example.utapCattle.service.repository.SellerMarketRepository;
import com.example.utapCattle.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SaleRepository saleRepository;
    private final CattleRepository cattleRepository;
    private final CommentRepository commentRepository;
    private final SellerMarketRepository sellerMarketRepository;
    private final WeightHistoryService weightHistoryService;
    private final MovementService movementService;

    public SaleServiceImpl(
            SaleRepository saleRepository,
            CattleRepository cattleRepository,
            CommentRepository commentRepository,
            SellerMarketRepository sellerMarketRepository,
            WeightHistoryService weightHistoryService,
            MovementService movementService) {
        this.saleRepository = saleRepository;
        this.cattleRepository = cattleRepository;
        this.commentRepository = commentRepository;
        this.sellerMarketRepository = sellerMarketRepository;
        this.weightHistoryService = weightHistoryService;
        this.movementService = movementService;
    }

    @Override
    public SaleDto sellCattle(SaleDto saleDto, Long userId) {

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
            weightHistory.setUserId(userId);
            weightHistoryService.saveWeightHistory(weightHistory);
        }
        final Movement movement = new Movement();
        movement.setCattleId(saleDto.getCattleId());
        movement.setPenId(Integer.parseInt(saleDto.getPenId() + ""));
        movement.setMovementDate(getCurrentFormattedDate());
        movement.setMovementId(userId);
        movement.setUserId(userId);
        movementService.saveMovement(movement);
        final Sale sale = new Sale();
        if (saleDto.getSaleId() != null) {
            sale.setSaleId(saleDto.getSaleId());
        } else {
            sale.setSaleId(saleRepository.getNextSequenceValue());
        }

        // Save to sale table
        sale.setSaleDate(saleDto.getSaleDate());
        sale.setSaleMarketId(saleDto.getSaleMarketId());
        sale.setUserId(userId);

        final Sale savedSale = saleRepository.save(sale);

        cattle.setWeightAtSale(saleDto.getWeight());
        cattle.setSaleId(savedSale.getSaleId());
        cattle = cattleRepository.save(cattle);
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
        List<Sale> existingSales = saleRepository.findAllBySaleMarketIdOrderBySaleDateDesc(saleMarketId);
        existingSales.forEach(sale -> {
            String originalDate = sale.getSaleDate();
            if (originalDate != null && !originalDate.isEmpty()) {
                sale.setSaleDate(DateUtils.formatToReadableDate(originalDate));
            }
        });
        return existingSales;
    }

    @Override
    public Sale getSaleBySaleId(Long saleId) {
        return saleRepository.findBySaleId(saleId);
    }

    @Override
    public List<CattleDto> getAllCattleBySaleId(Long saleId) { // Return List<CattleDto>

        String query = """
                SELECT
                    cat.id,
                    cat.cattleid,
                    cat.eartag,
                    cat.categoryid,
                    c.categorydesc,
                    cat.saleid,
                    cat.saleprice,
                    b.breedid,
                    b.breeddesc,
                    COALESCE(w_min.weight, 0) AS weightatpurchase,
                    COALESCE(w_max.weight, 0) AS weightatsale,
                    (w_max.weight-w_min.weight) / NULLIF(w_max.date_weighted - w_min.date_weighted, 0) AS dlwgfarm
                FROM CATTLE cat
                INNER JOIN category c ON cat.categoryid = c.categoryid
                INNER JOIN breed b ON cat.breedid = b.breedid
                inner join sale s on s.saleid = cat.saleid
                LEFT JOIN (
                    SELECT cattleid, weight,
                           TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') AS date_weighted,
                           ROW_NUMBER() OVER (PARTITION BY cattleid ORDER BY TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') ASC) AS rank
                    FROM weighthistory
                    WHERE weight > 25
                ) w_min ON cat.cattleid = w_min.cattleid AND w_min.rank = 1
                LEFT JOIN (
                    SELECT cattleid, weight,
                           TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') AS date_weighted,
                           ROW_NUMBER() OVER (PARTITION BY cattleid ORDER BY TO_DATE(weightdatetime, 'YYYY-MM-DD HH24:MI:SS') DESC) AS rank
                    FROM weighthistory
                    WHERE weight > 25
                ) w_max ON cat.cattleid = w_max.cattleid AND w_max.rank = 1
                WHERE cat.saleid = ?
                order by cat.updatedon DESC;
                                """;

        try {
            List<CattleDto> cattleData = jdbcTemplate.query(query, new RowMapper<CattleDto>() {
                @Override
                public CattleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CattleDto cattleData = new CattleDto();
                    cattleData.setId(rs.getLong("id"));
                    cattleData.setCattleId(rs.getLong("cattleid"));
                    cattleData.setEarTag(rs.getString("eartag"));
                    cattleData.setCategoryId(rs.getInt("categoryid"));
                    cattleData.setCategoryName(rs.getString("categorydesc"));
                    cattleData.setSaleId(rs.getLong("saleid"));
                    cattleData.setWeightAtPurchase(rs.getString("weightatpurchase"));
                    cattleData.setWeightAtSale(rs.getDouble("weightatsale"));
                    cattleData.setDlwgFarm(rs.getDouble("dlwgfarm"));
                    cattleData.setBreedId(rs.getInt("breedid"));
                    cattleData.setBreedName(rs.getString("breeddesc"));

                    // customer.setCustomerId(rs.getLong("customer_id"));
                    // customer.setCustomerName(rs.getString("customer_name"));
                    return cattleData;
                }
            }, saleId);
            return cattleData;
        } catch (Exception e) {
            logger.error("Exception while quering sale data: ", e);
            throw e;
        }

        // return cattleRepository.findAllBySaleId(saleId).stream().map(this::mapToDto)
        // // Map each Cattle to CattleDto
        // .collect(Collectors.toList());
    }

    @SuppressWarnings("deprecation")
    @Override
    public SaleTotalStats getSaleTotalStats(Long saleId) {
        String query = "SELECT " +
                "    COUNT(eartag) AS totalCattle, " +
                "    sum(case when EXTRACT(MONTH FROM AGE(c.dateofbirth::date, CURRENT_DATE::date)) + " +
                "    EXTRACT(YEAR FROM AGE(c.dateofbirth::date, CURRENT_DATE::date)) * 12 >= 30 then 1 else 0 end )AS totalOTM, "
                +
                "    SUM(weightatsale) as totalWeight " +
                "FROM " +
                "    cattle c " +
                "WHERE " +
                "    saleid = ? ";

        return jdbcTemplate.queryForObject(query, new Object[] { saleId }, (rs, rowNum) -> {
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
    // private static String getCurrentFormattedDate() {
    // final Date currentDate = new Date();
    // final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // return formatter.format(currentDate);
    // }
    private String getCurrentFormattedDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    @Override
    public Boolean checkForValidSaleDate(SaleDateRequest request) {
        return !saleRepository.existsBySaleDateAndSaleMarketId(request.getNewDate(), request.getSellerMarketId());
    }

    @Override
    public SaleDto keepCattle(SaleDto saleDto, Long userId) {

        Cattle cattle = cattleRepository.findByCattleId(saleDto.getCattleId())
                .orElseThrow(() -> new RuntimeException("Cattle not found"));
        /* New Entry into weight history table */
        if (saleDto.getWeight() != null) {
            final WeightHistory weightHistory = new WeightHistory();
            weightHistory.setCattleId(saleDto.getCattleId());
            weightHistory.setWeightDateTime(getCurrentFormattedDate());
            weightHistory.setWeight(saleDto.getWeight());
            weightHistory.setUserId(userId);
            weightHistoryService.saveWeightHistory(weightHistory);
        }

        /* New Entry into Movement table */
        final Movement movement = new Movement();
        movement.setCattleId(saleDto.getCattleId());
        movement.setPenId(Integer.parseInt(saleDto.getPenId() + ""));
        movement.setMovementDate(getCurrentFormattedDate());
        movement.setMovementId(userId);
        movement.setUserId(userId);
        movementService.saveMovement(movement);

        return saleDto;
    }

    @Override
    public void deleteSale(Long id) {
        Optional<Cattle> cattle = cattleRepository.findById(id);
        if (cattle.isPresent()) {
            // Delete the sale from cattle
            cattle.get().setSaleId(null); // Clear the sale ID
            cattleRepository.save(cattle.get());
        } else {
            throw new RuntimeException("Cattle not found");
        }
    }
}
