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

import kr.or.connect.resv.dto.Product;
import kr.or.connect.resv.service.ResvmanagerService;

@RestController
@RequestMapping(path="/products")
public class ResvmanagerApiController {
	@Autowired
	ResvmanagerService resvmanagerService;

	@GetMapping
	public Map<String, Object> products() {
		List<Product> products = resvmanagerService.getAllProducts();		
		int count = products.size();		
		Map<String, Object> map = new HashMap<>();
		map.put("productList", products);
		map.put("productCount", count);
		return map;
	}
	
	@GetMapping(params="start")
	public Map<String, Object> productFromStart(@RequestParam(value="start", required=true, defaultValue="0") Integer start) {
		List<Product> productFromStart = resvmanagerService.getLimitProducts(start);
		Map<String, Object> map = new HashMap<>();
		map.put("productLimitList", productFromStart);
		return map;
	}

	@GetMapping("/{id}")
	public Product productDetail( @PathVariable Integer id) {
		return resvmanagerService.getOneProduct(id);
	}
	
	@GetMapping(params= {"categoryId", "start"})
	public Map<String, Object> productFromStartAndCategory( @RequestParam(value="categoryId", required=true) Integer categoryId,
			@RequestParam(value="start", required=true, defaultValue="0") Integer start) {
		List<Product> productFromStartAndCategory = resvmanagerService.getLimitCategoryProducts(categoryId, start);
		Map<String, Object> map = new HashMap<>();
		map.put("productLimitList", productFromStartAndCategory);
		return map;
	}
}