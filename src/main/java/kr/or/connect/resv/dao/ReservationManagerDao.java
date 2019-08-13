package kr.or.connect.resv.dao;

import static kr.or.connect.resv.dao.ReservationManagerDaoSql.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.Category;
import kr.or.connect.resv.dto.model.Comment;
import kr.or.connect.resv.dto.model.CommentImage;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.dto.model.ProductImage;
import kr.or.connect.resv.dto.model.ProductPrice;
import kr.or.connect.resv.dto.model.Promotion;
import kr.or.connect.resv.dto.model.ReservationInfo;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.dto.model.ReservationPrice;
import kr.or.connect.resv.service.ProductService;

@Repository
public class ReservationManagerDao {
	public static final String IMAGE_TYPE = "th";

	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<ProductImage> productImageRowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);
	private RowMapper<Category> categoryRowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	private RowMapper<Promotion> promotionRowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
	private RowMapper<Comment> commentRowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	private RowMapper<CommentImage> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);
	private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<DisplayInfoImage> displayInfoImageRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);
	private RowMapper<ProductPrice> productPriceRowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);
	private RowMapper<ReservationInfo> reservationInfoRowMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
	private RowMapper<ReservationPrice> reservationPriceRowMapper = BeanPropertyRowMapper.newInstance(ReservationPrice.class);
	private RowMapper<ReservationResponse> reservationResponseRowMapper = BeanPropertyRowMapper.newInstance(ReservationResponse.class);
	private SimpleJdbcInsert insertReservationInfo;
	private SimpleJdbcInsert insertReservationInfoPrice;

	public ReservationManagerDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertReservationInfo = new SimpleJdbcInsert(dataSource).withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
		this.insertReservationInfoPrice = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price")
				.usingGeneratedKeyColumns("id");
	}

	public Integer selectCategoryCount(Integer categoryId) {
		Map<String, Object> param = new HashMap<>();
		param.put("categoryId", categoryId);
		return jdbc.queryForObject(SELECT_CATEGORY_COUNT, param, Integer.class);
	}

	public List<Product> selectLimitProducts(Integer start) {
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", ProductService.LIMIT);
		return jdbc.query(SELECT_LIMIT_PRODUCTS, params, productRowMapper);
	}

	public List<Product> selectLimitCategoryProducts(Integer categoryId, Integer start) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);
		params.put("limit", ProductService.LIMIT);
		return jdbc.query(SELECT_LIMIT_CATEGORY_PRODUCTS, params, productRowMapper);
	}

	public String selectFileName(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("imgtype", IMAGE_TYPE);
		return jdbc.queryForObject(SELECT_FILENAME, params, String.class);
	}

	public List<ProductImage> selectProductImageFileNames(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.query(SELECT_PRODUCT_IMAGE_FILENAMES, params, productImageRowMapper);
	}

	public List<Category> selectCategories() {
		return jdbc.query(SELECT_CATEGORIES, categoryRowMapper);
	}

	public List<Promotion> selectAllPromotions() {
		return jdbc.query(SELECT_ALL_PROMOTIONS, promotionRowMapper);
	}

	public List<Comment> selectCommentsByDisplayInfoId(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("displayInfoId", id);
		return jdbc.query(SELECT_COMMENTS_BY_DISPLAY_INFO_ID, param, commentRowMapper);
	}

	public List<CommentImage> selectCommentImagesByDisplayInfoId(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("displayInfoId", id);
		return jdbc.query(SELECT_COMMENT_IMAGES_BY_DISPLAY_INFO_ID, param, commentImageRowMapper);
	}

	public DisplayInfo selectDisplayInfoByDisplayInfoId(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("displayInfoId", id);
		return jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID, param, displayInfoRowMapper);
	}

	public DisplayInfoImage selectDisplayInfoImageByDisplayInfoId(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("displayInfoId", id);
		return jdbc.queryForObject(SELECT_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID, param, displayInfoImageRowMapper);
	}

	public List<ProductPrice> selectProductPricesByProductId(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("productId", id);
		return jdbc.query(SELECT_PRODUCT_PRICES, param, productPriceRowMapper);
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

	public List<ReservationInfo> selectReservationInfoByReservationEmail(String reservationEmail) {
		Map<String, Object> param = new HashMap<>();
		param.put("reservationEmail", reservationEmail);
		return jdbc.query(SELECT_RESERVATION_INFO_BY_RESERVATION_EMAIL, param, reservationInfoRowMapper);
	}

	public Integer selectTotalPriceByReservationInfoId(Integer reservationInfoId) {
		Map<String, Object> param = new HashMap<>();
		param.put("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(SELECT_TOTAL_PRICE_BY_RESERVATION_INFO_ID, param, Integer.class);
	}
	
	public ReservationResponse selectReservationResponseById(Integer reservationInfoId) {
		Map<String, Object> param = new HashMap<>();
		param.put("reservationInfoId", reservationInfoId);
		return jdbc.queryForObject(SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID, param, reservationResponseRowMapper);
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
}
