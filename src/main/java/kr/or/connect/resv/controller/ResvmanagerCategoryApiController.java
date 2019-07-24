package kr.or.connect.resv.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/categories")
public class ResvmanagerCategoryApiController extends CommonController {
	
	@GetMapping()
	public Map<String, Object> getCategories() {
		return resvmanagerService.getCategories();
	}
}
