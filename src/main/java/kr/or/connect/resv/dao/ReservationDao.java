package kr.or.connect.resv.dao;

import static kr.or.connect.resv.dao.ReservationDaoSql.SELECT_NESTED_RESERVATION_INFO_BY_RESERVATION_EMAIL;
import static kr.or.connect.resv.dao.ReservationDaoSql.SELECT_RESERVATION_COMMENT_BY_COMMENT_ID;
import static kr.or.connect.resv.dao.ReservationDaoSql.SELECT_RESERVATION_COMMENT_IMAGE_BY_COMMENT_ID;
import static kr.or.connect.resv.dao.ReservationDaoSql.SELECT_RESERVATION_PRICE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.resv.dao.ReservationDaoSql.SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID;
import static kr.or.connect.resv.dao.ReservationDaoSql.UPDATE_RESERVAION_INFO_CANCEL;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import kr.or.connect.resv.dto.CommentResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.CommentImage;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.ReservationInfo;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.dto.model.ReservationPrice;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertReservationUserComment;
    private SimpleJdbcInsert insertReservationUserCommentImage;
    private SimpleJdbcInsert insertFileInfo;
    private SimpleJdbcInsert insertReservationInfo;
    private SimpleJdbcInsert insertReservationInfoPrice;
    private RowMapper<ReservationPrice> reservationPriceRowMapper = BeanPropertyRowMapper.newInstance(
            ReservationPrice.class);
    private RowMapper<ReservationResponse> reservationResponseRowMapper = BeanPropertyRowMapper.newInstance(
            ReservationResponse.class);
    private RowMapper<CommentImage> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);
    private RowMapper<CommentResponse> commentResponseRowMapper = BeanPropertyRowMapper.newInstance(
            CommentResponse.class);
    private RowMapper<ReservationInfo> reservationInfoRowMapper = BeanPropertyRowMapper.newInstance(
            ReservationInfo.class);
    private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);

    private RowMapper<ReservationInfo> reservationInfoNestedRowMapper = new NestedRowMapper(ReservationInfo.class);

    public ReservationDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertReservationUserComment = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("id");
        this.insertReservationUserCommentImage = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_user_comment_image")
                .usingGeneratedKeyColumns("id");
        this.insertFileInfo = new SimpleJdbcInsert(dataSource)
                .withTableName("file_info")
                .usingGeneratedKeyColumns("id");
        this.insertReservationInfo = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_info")
                .usingGeneratedKeyColumns("id");
        this.insertReservationInfoPrice = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_info_price")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationInfo> selectNestedReservationInfoByReservationEmail(String reservationEmail) {
        Map<String, Object> param = new HashMap<>();
        param.put("reservationEmail", reservationEmail);
        return jdbc.query(SELECT_NESTED_RESERVATION_INFO_BY_RESERVATION_EMAIL, param, reservationInfoNestedRowMapper);
    }

    public CommentImage selectReservationCommentImageByCommentId(Integer reservationUserCommentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("commentId", reservationUserCommentId);
        try {
            return jdbc.queryForObject(SELECT_RESERVATION_COMMENT_IMAGE_BY_COMMENT_ID, param, commentImageRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public CommentResponse selectReservationCommentByCommentId(Integer reservationUserCommentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("commentId", reservationUserCommentId);
        return jdbc.queryForObject(SELECT_RESERVATION_COMMENT_BY_COMMENT_ID, param, commentResponseRowMapper);
    }

    public Integer insertReservationUserComment(CommentResponse commentParams) {
        Map<String, Object> params = new HashMap<>();
        LocalDateTime createDate = LocalDateTime.now();
        params.put("product_id", commentParams.getProductId());
        params.put("reservation_info_id", commentParams.getReservationInfoId());
        params.put("score", commentParams.getScore());
        params.put("comment", commentParams.getComment());
        params.put("create_date", createDate);
        params.put("modify_date", createDate);

        return insertReservationUserComment.executeAndReturnKey(params).intValue();
    }

    public Integer insertReservationUserCommentImage(CommentResponse commentParams, Integer fileInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("reservation_user_comment_id", commentParams.getCommentId());
        params.put("reservation_info_id", commentParams.getReservationInfoId());
        params.put("file_id", fileInfoId);

        return insertReservationUserCommentImage.executeAndReturnKey(params).intValue();
    }

    public Integer insertFileInfo(String fileName, String saveFileName, String contentType) {
        Map<String, Object> params = new HashMap<>();
        LocalDateTime createDate = LocalDateTime.now();
        params.put("file_name", fileName);
        params.put("save_file_name", saveFileName);
        params.put("content_type", contentType);
        params.put("delete_flag", 0);
        params.put("create_date", createDate);
        params.put("modify_date", createDate);

        return insertFileInfo.executeAndReturnKey(params).intValue();
    }

    public Integer insertReservationInfo(ReservationParam reservationParam) {
        Map<String, Object> params = new HashMap<>();
        params.put("display_info_id", reservationParam.getDisplayInfoId());
        params.put("product_id", reservationParam.getProductId());
        params.put("create_date", reservationParam.getReservationYearMonthDay());
        params.put("reservation_date", reservationParam.getReservationYearMonthDay());
        params.put("modify_date", reservationParam.getReservationYearMonthDay());
        params.put("reservation_email", reservationParam.getReservationEmail());
        params.put("reservation_name", reservationParam.getReservationName());
        params.put("reservation_tel", reservationParam.getReservationTelephone());
        params.put("cancel_flag", 0);
        return insertReservationInfo.executeAndReturnKey(params).intValue();
    }

    public Integer insertReservationInfoPrice(ReservationPrice reservationPrice) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationPrice);
        return insertReservationInfoPrice.executeAndReturnKey(params).intValue();
    }

    public ReservationResponse selectReservationResponseById(Integer reservationInfoId) {
        Map<String, Object> param = new HashMap<>();
        param.put("reservationInfoId", reservationInfoId);
        return jdbc.queryForObject(SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID, param,
                reservationResponseRowMapper);
    }

    public List<ReservationPrice> selectReservationPricesById(Integer reservationInfoId) {
        Map<String, Object> param = new HashMap<>();
        param.put("reservationInfoId", reservationInfoId);
        return jdbc.query(SELECT_RESERVATION_PRICE_BY_RESERVATION_INFO_ID, param, reservationPriceRowMapper);
    }

    public Integer cancelReservationInfo(Integer reservationInfoId) {
        Map<String, Object> param = new HashMap<>();
        param.put("reservationInfoId", reservationInfoId);
        return jdbc.update(UPDATE_RESERVAION_INFO_CANCEL, param);
    }

    public DisplayInfo selectDisplayInfoByDisplayInfoId(Integer id) {
        Map<String, Object> param = new HashMap<>();
        param.put("displayInfoId", id);
        return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID, param, displayInfoRowMapper);
    }
}
