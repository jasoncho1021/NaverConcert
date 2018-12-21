package kr.or.connect.resv.dao;

import static kr.or.connect.resv.dao.ResvmanagerDaoSql.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.dto.Category;
import kr.or.connect.resv.dto.Product;
import kr.or.connect.resv.dto.Promotion;
import kr.or.connect.resv.service.ResvmanagerService;

@Repository
public class ResvmanagerDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<Category> categoryRowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	private RowMapper<Promotion> promotionRowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
	
	public ResvmanagerDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Product> selectAllProducts() {
		return jdbc.query(SELECT_ALL_PRODUCTS, productRowMapper);
	}
	
	public Product selectOneProduct(Integer id) {
		Map<String, Object> param = new HashMap<>();
		param.put("productId", id);
		return jdbc.queryForObject(SELECT_ONE_PRODUCT, param, productRowMapper);
	}
	
	public List<Product> selectLimitProducts(Integer start) {
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", ResvmanagerService.LIMIT);
		return jdbc.query(SELECT_LIMIT_PRODUCTS, params, productRowMapper);
	}
	
	public List<Product> selectLimitCategoryProducts(Integer categoryId, Integer start) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);
		params.put("limit", ResvmanagerService.LIMIT);
		return jdbc.query(SELECT_LIMIT_CATEGORY_PRODUCTS, params, productRowMapper);
	}
	
	public Integer selectProductsCount() {
		return jdbc.queryForObject(SELECT_PRODUCTS_COUNT, Collections.emptyMap(), Integer.class);
	}
	
	public String selectFileName(Integer id, String type) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("imgtype", type);
		return jdbc.queryForObject(SELECT_FILENAME, params, String.class);
	}
	
	public List<Category> selectCategories() {
		return jdbc.query(SELECT_CATEGORIES, categoryRowMapper);
	}
	
	public Integer selectCategoriesCount() {
		return jdbc.queryForObject(SELECT_CATEGORIES_COUNT, Collections.emptyMap(), Integer.class);
	}
	
	public List<Promotion> selectAllPromotions() {
		return jdbc.query(SELECT_ALL_PROMOTIONS, promotionRowMapper);
	}
}
