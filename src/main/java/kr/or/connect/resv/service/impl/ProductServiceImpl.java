package kr.or.connect.resv.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.or.connect.resv.dao.ReservationManagerDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ReservationManagerDao reservationManagerDao;

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getLimitProducts(Integer start) {
		List<Product> productList = reservationManagerDao.selectLimitProducts(start);
		ProductDTO productDTO = getProductDTO(productList);
		productDTO.setTotalCount(productList.size());
		return productDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getLimitCategoryProducts(Integer categoryId, Integer start) {
		List<Product> productList = reservationManagerDao.selectLimitCategoryProducts(categoryId, start);
		ProductDTO productDTO = getProductDTO(productList);
		productDTO.setTotalCount(getCategoryCount(categoryId));
		return productDTO;
	}

	private ProductDTO getProductDTO(List<Product> productList) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setItems(productList);
		return productDTO;
	}

	@Transactional(readOnly = true)
	public Integer getCategoryCount(Integer categoryId) {
		return reservationManagerDao.selectCategoryCount(categoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public DisplayInfoDTO getDisplayInfoById(Integer displayInfoId) {
		List<Comment> comments = getCommentsByDisplayInfoId(displayInfoId);
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

	private List<Comment> getCommentsByDisplayInfoId(Integer displayInfoId) {
		Map<Integer, Comment> commentMap = new HashMap<>();

		List<Comment> comments = getComments(displayInfoId);
		for (Comment comment : comments) {
			commentMap.put(comment.getCommentId(), comment);
		}

		List<CommentImage> commentImages = getCommentImages(displayInfoId);
		for (CommentImage commentImage : commentImages) {
			commentMap.get(commentImage.getReservationUserCommentId()).getCommentImages().add(commentImage);
		}
		return comments;
	}

	private double getAverageScore(List<Comment> comments) {
		return comments.stream().mapToDouble(Comment::getScore).average().orElse(0);
	}

	private List<Comment> getComments(Integer displayInfoId) {
		return reservationManagerDao.selectCommentsByDisplayInfoId(displayInfoId);
	}

	private List<CommentImage> getCommentImages(Integer displayInfoId) {
		return reservationManagerDao.selectCommentImagesByDisplayInfoId(displayInfoId);
	}

	private DisplayInfo getDisplayInfo(Integer displayInfoId) {
		return reservationManagerDao.selectDisplayInfoByDisplayInfoId(displayInfoId);
	}

	private DisplayInfoImage getDisplayInfoImage(Integer displayInfoId) {
		return reservationManagerDao.selectDisplayInfoImageByDisplayInfoId(displayInfoId);
	}

	private List<ProductImage> getProductImages(Integer productId) {
		return reservationManagerDao.selectProductImageFileNames(productId);
	}

	private List<ProductPrice> getProductPrices(Integer productId) {
		return reservationManagerDao.selectProductPricesByProductId(productId);
	}
}
