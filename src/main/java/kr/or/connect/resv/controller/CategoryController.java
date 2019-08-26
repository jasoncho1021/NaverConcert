package kr.or.connect.resv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.dto.CategoryDTO;
import kr.or.connect.resv.service.CategoryService;

@RestController
@RequestMapping(path="/api/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping()
	public CategoryDTO getCategories() {
		return categoryService.getCategories();
	}
}
