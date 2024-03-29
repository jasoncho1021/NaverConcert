package kr.or.connect.resv.controller;

import kr.or.connect.resv.dto.PromotionDTO;
import kr.or.connect.resv.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/promotions")
public class PromotionController {
	@Autowired
	private PromotionService promotionService;

	@GetMapping
	public PromotionDTO getPromo() {
		return promotionService.getAllPromotions();
	}
}
