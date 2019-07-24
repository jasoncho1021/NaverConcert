package kr.or.connect.resv.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.dto.model.Promotion;

public interface ResvmanagerService {
	public static final String IMAGE_FILE_ROOT_PATH = "img/";
	public static final String IMAGE_TYPE = "ma";
	public static final String IMAGE_TYPE_PROMO = "th";
	public static final Integer LIMIT = 4;
	public List<Product> getAllProducts();
	public Product getOneProduct(Integer id);
	public List<Product> getLimitProducts(Integer start);
	public List<Product> getLimitCategoryProducts(Integer categoryId, Integer start);
	public String getFileName(Integer id, String type);
	public Map<String, Object> getCategories();
	public List<Promotion> getAllPromotions();
}
