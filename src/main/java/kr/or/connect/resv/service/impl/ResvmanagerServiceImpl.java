package kr.or.connect.resv.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ResvmanagerDao;
import kr.or.connect.resv.dto.model.Category;
import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.dto.model.Promotion;
import kr.or.connect.resv.service.ResvmanagerService;

@Service
public class ResvmanagerServiceImpl implements ResvmanagerService {
	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly = true)
	public List<Product> getLimitProducts(Integer start) {
		List<Product> productList = resvmanagerDao.selectLimitProducts(start);

		Iterator<Product> productIterator = productList.iterator();
		while (productIterator.hasNext()) {
			Product product = productIterator.next();
			product.setImageUrl(IMAGE_FILE_ROOT_PATH + getFileName(product.getId(), IMAGE_TYPE));
		}

		return productList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getLimitCategoryProducts(Integer categoryId, Integer start) {
		List<Product> productList = resvmanagerDao.selectLimitCategoryProducts(categoryId, start);

		Iterator<Product> productIterator = productList.iterator();
		while (productIterator.hasNext()) {
			Product product = productIterator.next();
			product.setImageUrl(IMAGE_FILE_ROOT_PATH + getFileName(product.getId(), IMAGE_TYPE));
		}

		return productList;
	}

	@Override
	@Transactional(readOnly = true)
	public String getFileName(Integer id, String type) {
		return resvmanagerDao.selectFileName(id, type);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
		List<Product> list = resvmanagerDao.selectAllProducts();
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Product getOneProduct(Integer id) {
		return resvmanagerDao.selectOneProduct(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getCategories() {
		List<Category> items = resvmanagerDao.selectCategories();
		Map<String, Object> map = new HashMap<>();
		map.put("items", items);
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Promotion> getAllPromotions() {
		List<Promotion> promotionList = resvmanagerDao.selectAllPromotions();

		Iterator<Promotion> promotionIterator = promotionList.iterator();
		while (promotionIterator.hasNext()) {
			Promotion promotion = promotionIterator.next();
			promotion.setProductImageUrl(IMAGE_FILE_ROOT_PATH + getFileName(promotion.getId(), IMAGE_TYPE_PROMO));
		}

		return promotionList;
	}
}
