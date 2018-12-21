package kr.or.connect.resv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.service.ResvmanagerService;

@RestController
@RequestMapping(path="/categories")
public class ResvmanagerCategoryApiController {
	@Autowired
	ResvmanagerService resvmanagerService;
	
	@GetMapping()
	public Map<String, Object> getCategories() {
		return resvmanagerService.getCategories();
	}
}
