package kr.or.connect.resv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ResvmanagerDao;
import kr.or.connect.resv.dto.CategoryDTO;
import kr.or.connect.resv.dto.model.Category;
import kr.or.connect.resv.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly = true)
	public CategoryDTO getCategories() {
		List<Category> categoryItems = resvmanagerDao.selectCategories();
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setItems(categoryItems);
		return categoryDTO;
	}

}
