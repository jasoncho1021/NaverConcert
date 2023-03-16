package kr.or.connect.resv.controller;

import kr.or.connect.resv.dto.CategoryDTO;
import kr.or.connect.resv.exception.UnAuthenticationException;
import kr.or.connect.resv.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public CategoryDTO getCategories() {
		return categoryService.getCategories();
	}

	@GetMapping("/error")
	public ResponseEntity<CategoryDTO> getCategorieError() throws Exception {

		if (true) {
			throw new UnAuthenticationException();
		} else {
			//throw new IllegalStateException("Basic Error");
		}

		//throw new IllegalStateException("Basic Error");

		return new ResponseEntity<>(new CategoryDTO(), HttpStatus.SERVICE_UNAVAILABLE);
	}
}
