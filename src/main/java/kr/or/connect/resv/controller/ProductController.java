package kr.or.connect.resv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.dto.DisplayInfoDTO;
import kr.or.connect.resv.dto.ProductDTO;
import kr.or.connect.resv.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	private static final int DEAFAULT_CATEGROY_ID = -1;

	@GetMapping
	public ProductDTO getAllproducts() {
		return getProductFromStartAndCategory(DEAFAULT_CATEGROY_ID, 0);
	}

	@GetMapping(params = "start")
	public ProductDTO getProductsByStart(
			@RequestParam(value = "start", required = true, defaultValue = "0") Integer start) {
		return getProductFromStartAndCategory(DEAFAULT_CATEGROY_ID, start);
	}

	@GetMapping(params = "categoryId")
	public ProductDTO getProductsByCategoryId(
			@RequestParam(value = "categoryId", required = true, defaultValue = "0") Integer categoryId) {
		return getProductFromStartAndCategory(categoryId, 0);
	}

	@GetMapping(params = { "categoryId", "start" })
	public ProductDTO getProductFromStartAndCategory(
			@RequestParam(value = "categoryId", required = false, defaultValue = "1") Integer categoryId,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start) {
		if (categoryId == DEAFAULT_CATEGROY_ID) {
			return productService.getLimitProducts(start);
		} else {
			return productService.getLimitCategoryProducts(categoryId, start);
		}
	}

	@GetMapping("/{displayInfoId}")
	public DisplayInfoDTO productDetail(@PathVariable Integer displayInfoId) {
		return productService.getDisplayInfoById(displayInfoId);
	}
}
