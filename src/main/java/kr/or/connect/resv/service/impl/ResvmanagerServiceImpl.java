package kr.or.connect.resv.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ResvmanagerDao;
import kr.or.connect.resv.dto.Category;
import kr.or.connect.resv.dto.Product;
import kr.or.connect.resv.dto.Promotion;
import kr.or.connect.resv.service.ResvmanagerService;

@Service
public class ResvmanagerServiceImpl implements ResvmanagerService {
	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly=true)
	public List<Product> getLimitProducts(Integer start) {
		List<Product> list = resvmanagerDao.selectLimitProducts(start);
		return list;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Product> getLimitCategoryProducts(Integer categoryId, Integer start) {
		List<Product> list = resvmanagerDao.selectLimitCategoryProducts(categoryId, start);
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public Integer getProductsCount() {
		return resvmanagerDao.selectProductsCount();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> getAllProducts() {
		List<Product> list = resvmanagerDao.selectAllProducts();
		return list;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Product getOneProduct(Integer id) {
		return resvmanagerDao.selectOneProduct(id);
	}

	@Override
	@Transactional(readOnly=true)
	public String getFileName(Integer id, String type) {
		return resvmanagerDao.selectFileName(id, type);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> getCategories() {
		List<Category> items = resvmanagerDao.selectCategories();
		Integer size = resvmanagerDao.selectCategoriesCount();
		Map<String, Object> map = new HashMap<>();
		map.put("items", items);
		map.put("size", size);
		return map; 
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Promotion> getAllPromotions() {
		List<Promotion> list = resvmanagerDao.selectAllPromotions();
		return list;
	}
}
