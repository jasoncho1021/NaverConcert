package kr.or.connect.resv.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.dto.model.Promotion;

@RestController
@RequestMapping(path="/api/promotions")
public class ResvmanagerPromotionApiController extends CommonController {

	@GetMapping
	public Map<String, Object> getPromo() {
		List<Promotion> promotions = resvmanagerService.getAllPromotions();
		Map<String, Object> map = new HashMap<>();
		map.put("items", promotions);
		return map;
	}
}
