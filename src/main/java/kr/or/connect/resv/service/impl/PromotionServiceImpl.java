package kr.or.connect.resv.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ResvmanagerDao;
import kr.or.connect.resv.dto.PromotionDTO;
import kr.or.connect.resv.dto.model.Promotion;
import kr.or.connect.resv.service.PromotionService;
import kr.or.connect.resv.utils.Util;

@Service
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly = true)
	public PromotionDTO getAllPromotions() {
		List<Promotion> promotionList = resvmanagerDao.selectAllPromotions();

		Iterator<Promotion> promotionIterator = promotionList.iterator();
		while (promotionIterator.hasNext()) {
			Promotion promotion = promotionIterator.next();
			promotion.setProductImageUrl(Util.IMAGE_FILE_ROOT_PATH + getFileName(promotion.getId(), Util.IMAGE_TYPE_PROMO));
		}

		PromotionDTO promotionDTO = new PromotionDTO();
		promotionDTO.setItems(promotionList);
		return promotionDTO;
	}

	@Transactional(readOnly = true)
	private String getFileName(Integer id, String type) {
		return resvmanagerDao.selectFileName(id, type);
	}
}
