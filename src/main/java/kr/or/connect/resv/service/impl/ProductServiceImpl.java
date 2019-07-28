package kr.or.connect.resv.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ResvmanagerDao;
import kr.or.connect.resv.dto.DisplayInfoDTO;
import kr.or.connect.resv.dto.ProductDTO;
import kr.or.connect.resv.dto.model.Comment;
import kr.or.connect.resv.dto.model.CommentImage;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.Product;
import kr.or.connect.resv.dto.model.ProductImage;
import kr.or.connect.resv.dto.model.ProductPrice;
import kr.or.connect.resv.service.ProductService;
import kr.or.connect.resv.utils.Util;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getLimitProducts(Integer start) {
		List<Product> productList = resvmanagerDao.selectLimitProducts(start);

		setProductImageUrl(productList);

		ProductDTO productDTO = new ProductDTO();
		productDTO.setItems(productList);
		productDTO.setTotalCount(productList.size());

		return productDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getLimitCategoryProducts(Integer categoryId, Integer start) {
		List<Product> productList = resvmanagerDao.selectLimitCategoryProducts(categoryId, start);

		setProductImageUrl(productList);

		ProductDTO productDTO = new ProductDTO();
		productDTO.setItems(productList);
		int totalCount = getCategoryCount(categoryId);
		productDTO.setTotalCount(totalCount);

		return productDTO;
	}

	private void setProductImageUrl(List<Product> productList) {
		Iterator<Product> productIterator = productList.iterator();
		while (productIterator.hasNext()) {
			Product product = productIterator.next();
			product.setProductImageUrl(Util.IMAGE_FILE_ROOT_PATH + getFileName(product.getProductId(), Util.IMAGE_TYPE_PROMO));
		}
	}

	@Transactional(readOnly = true)
	public Integer getCategoryCount(Integer categoryId) {
		return resvmanagerDao.selectCategoryCount(categoryId);
	}

	@Transactional(readOnly = true)
	private String getFileName(Integer id, String type) {
		return resvmanagerDao.selectFileName(id, type);
	}

	@Override
	@Transactional(readOnly = true)
	public DisplayInfoDTO getDisplayInfoById(Integer displayInfoId) {

		List<Comment> comments = getComments(displayInfoId);

		Map<Integer, Comment> commentMap = new HashMap<>();
		Iterator<Comment> commentIter = comments.iterator();
		while (commentIter.hasNext()) {
			Comment comment = commentIter.next();
			commentMap.put(comment.getCommentId(), comment);
		}

		List<CommentImage> commentImages = getCommentImages(displayInfoId);
		Iterator<CommentImage> commentImageIter = commentImages.iterator();
		while (commentImageIter.hasNext()) {
			CommentImage commentImage = commentImageIter.next();
			commentMap.get(commentImage.getReservationUserCommentId()).getCommentImages().add(commentImage);
		}

		DisplayInfo displayInfo = getDisplayInfo(displayInfoId);
		DisplayInfoImage displayInfoImage = getDisplayInfoImage(displayInfoId);

		Integer productId = displayInfo.getProductId();
		List<ProductImage> productImages = getProductImages(productId);
		List<ProductPrice> productPrices = getProductPrices(productId);

		DisplayInfoDTO displayInfoDTO = new DisplayInfoDTO();
		displayInfoDTO.setComments(comments);
		displayInfoDTO.setAverageScore(getAverageScore(comments));
		displayInfoDTO.setDisplayInfo(displayInfo);
		displayInfoDTO.setDisplayInfoImage(displayInfoImage);
		displayInfoDTO.setProductImages(productImages);
		displayInfoDTO.setProductPrices(productPrices);
		return displayInfoDTO;
	}

	private double getAverageScore(List<Comment> comment) {
		Iterator<Comment> commentIter = comment.iterator();
		double sum = 0;
		while (commentIter.hasNext()) {
			sum += commentIter.next().getScore();
		}
		return sum / (comment.size());
	}

	@Transactional(readOnly = true)
	private List<Comment> getComments(Integer displayInfoId) {
		return resvmanagerDao.selectCommentsByDisplayInfoId(displayInfoId);
	}

	@Transactional(readOnly = true)
	private List<CommentImage> getCommentImages(Integer displayInfoId) {
		return resvmanagerDao.selectCommentImagesByDisplayInfoId(displayInfoId);
	}

	@Transactional(readOnly = true)
	private DisplayInfo getDisplayInfo(Integer displayInfoId) {
		return resvmanagerDao.selectDisplayInfoByDisplayInfoId(displayInfoId);
	}

	@Transactional(readOnly = true)
	private DisplayInfoImage getDisplayInfoImage(Integer displayInfoId) {
		return resvmanagerDao.selectDisplayInfoImageByDisplayInfoId(displayInfoId);
	}

	@Transactional(readOnly = true)
	private List<ProductImage> getProductImages(Integer productId) {
		return resvmanagerDao.selectProductImageFileNames(productId);
	}

	@Transactional(readOnly = true)
	private List<ProductPrice> getProductPrices(Integer productId) {
		return resvmanagerDao.selectProductPricesByProductId(productId);
	}

}
