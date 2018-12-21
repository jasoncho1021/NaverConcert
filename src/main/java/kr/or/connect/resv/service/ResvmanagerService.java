package kr.or.connect.resv.service;

import java.util.List;
import java.util.Map;

import kr.or.connect.resv.dto.Product;
import kr.or.connect.resv.dto.Promotion;

public interface ResvmanagerService {
	public static final Integer LIMIT = 4;
	public List<Product> getAllProducts();
	public Product getOneProduct(Integer id);
	public List<Product> getLimitProducts(Integer start);
	public List<Product> getLimitCategoryProducts(Integer categoryId, Integer start);
	public Integer getProductsCount();
	public String getFileName(Integer id, String type);
	public Map<String, Object> getCategories();
	public List<Promotion> getAllPromotions();
}
