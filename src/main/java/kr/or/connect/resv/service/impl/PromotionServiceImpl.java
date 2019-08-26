package kr.or.connect.resv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ReservationManagerDao;
import kr.or.connect.resv.dto.PromotionDTO;
import kr.or.connect.resv.dto.model.Promotion;
import kr.or.connect.resv.service.PromotionService;

@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	private ReservationManagerDao reservationManagerDao;

	@Override
	@Transactional(readOnly = true)
	public PromotionDTO getAllPromotions() {
		List<Promotion> promotionList = reservationManagerDao.selectAllPromotions();
		PromotionDTO promotionDTO = new PromotionDTO();
		promotionDTO.setItems(promotionList);
		return promotionDTO;
	}
}
