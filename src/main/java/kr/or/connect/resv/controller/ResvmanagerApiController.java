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
public class ResvmanagerApiController extends CommonController {

	@Autowired
	ProductService productService;

	@GetMapping
	public ProductDTO getAllproducts() {
		System.out.println("yuumy");
		return productFromStartAndCategory(-1, 0);
	}

	@GetMapping(params = "start")
	public ProductDTO getProductsByStart(
			@RequestParam(value = "start", required = true, defaultValue = "0") Integer start) {
		System.out.println("staart");
		return productFromStartAndCategory(-1, start);
	}

	@GetMapping(params = "categoryId")
	public ProductDTO getProductsByCategoryId(
			@RequestParam(value = "categoryId", required = true, defaultValue = "0") Integer categoryId) {
		System.out.println("categorr");
		return productFromStartAndCategory(categoryId, 0);
	}

	@GetMapping(params = { "categoryId", "start" })
	public ProductDTO productFromStartAndCategory(
			@RequestParam(value = "categoryId", required = false, defaultValue = "1") Integer categoryId,
			@RequestParam(value = "start", required = false, defaultValue = "0") Integer start) {
		System.out.println("come!");

		if (categoryId == -1) {
			return resvmanagerService.getLimitProducts(start);
		} else {
			return resvmanagerService.getLimitCategoryProducts(categoryId, start);
		}
	}

	@GetMapping("/{displayInfoId}")
	public DisplayInfoDTO productDetail(@PathVariable Integer displayInfoId) {
		return productService.getDisplayInfoById(displayInfoId);
	}
}