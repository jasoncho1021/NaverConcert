package kr.or.connect.resv.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.dto.DisplayInfoDTO;
import kr.or.connect.resv.dto.ProductDTO;
import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ResvmanagerApiController extends CommonController {

	@Autowired
	ProductService productService;

	@GetMapping(params = "start")
	public ProductDTO productFromStart(
			@RequestParam(value = "start", required = true, defaultValue = "0") Integer start) {
		List<Product> productFromStart = resvmanagerService.getLimitProducts(start);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setTotalCount(productFromStart.size());
		productDTO.setProductLimitList(productFromStart);
		return productDTO;
	}

	@GetMapping("/{displayInfoId}")
	public DisplayInfoDTO productDetail(@PathVariable Integer displayInfoId) {
		return productService.getDisplayInfoById(displayInfoId);
	}

	@GetMapping(params = { "categoryId", "start" })
	public Map<String, Object> productFromStartAndCategory(
			@RequestParam(value = "categoryId", required = true) Integer categoryId,
			@RequestParam(value = "start", required = true, defaultValue = "0") Integer start) {
		List<Product> productFromStartAndCategory = resvmanagerService.getLimitCategoryProducts(categoryId, start);
		Map<String, Object> map = new HashMap<>();
		map.put("productLimitList", productFromStartAndCategory);
		map.put("totalCount", productFromStartAndCategory.size());
		return map;
	}
}