package kr.or.connect.resv.dao;

import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_ALL_PROMOTIONS;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_CATEGORIES;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_CATEGORY_COUNT;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_COMMENTS_BY_DISPLAY_INFO_ID;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_COMMENT_IMAGES_BY_DISPLAY_INFO_ID;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_LIMIT_CATEGORY_PRODUCTS;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_LIMIT_PRODUCTS;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_PRODUCT_IMAGE_FILENAMES;
import static kr.or.connect.resv.dao.ReservationManagerDaoSql.SELECT_PRODUCT_PRICES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import kr.or.connect.resv.dto.model.Category;
import kr.or.connect.resv.dto.model.Comment;
import kr.or.connect.resv.dto.model.CommentImage;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.dto.model.ProductImage;
import kr.or.connect.resv.dto.model.ProductPrice;
import kr.or.connect.resv.dto.model.Promotion;
import kr.or.connect.resv.service.ProductService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationManagerDao {
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

	public ReservationManagerDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
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
}
