package kr.or.connect.resv.service;

import kr.or.connect.resv.dto.DisplayInfoDTO;
import kr.or.connect.resv.dto.ProductDTO;

public interface ProductService {
	public static final Integer LIMIT = 4;
	public ProductDTO getLimitProducts(Integer start);
	public ProductDTO getLimitCategoryProducts(Integer categoryId, Integer start);
	public DisplayInfoDTO getDisplayInfoById(Integer id);
}
