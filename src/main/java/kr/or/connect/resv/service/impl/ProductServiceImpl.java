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
import kr.or.connect.resv.dto.model.Comment;
import kr.or.connect.resv.dto.model.CommentImage;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.ImageType;
import kr.or.connect.resv.dto.model.ProductImage;
import kr.or.connect.resv.dto.model.ProductPrice;
import kr.or.connect.resv.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ResvmanagerDao resvmanagerDao;

	@Override
	@Transactional(readOnly = true)
	public DisplayInfoDTO getDisplayInfoById(Integer displayInfoId) {

		List<Comment> comments = getComments(displayInfoId);

		Map<Integer, Comment> map = new HashMap<>();
		Iterator<Comment> commentIter = comments.iterator();
		while (commentIter.hasNext()) {
			Comment comment = commentIter.next();
			map.put(comment.getCommentId(), comment);
		}

		List<CommentImage> commentImages = getCommentImages(displayInfoId);
		Iterator<CommentImage> commentImageIter = commentImages.iterator();
		while (commentImageIter.hasNext()) {
			CommentImage commentImage = commentImageIter.next();
			map.get(commentImage.getReservationUserCommentId()).getCommentImages().add(commentImage);
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

	private boolean enumExceptionChecker(List<ProductImage> productImageList) {
		Iterator<ProductImage> productImageIter = productImageList.iterator();
		while (productImageIter.hasNext()) {
			ProductImage productImage = productImageIter.next();

			boolean imageTypeValidator = false;
			for (ImageType imageType : ImageType.values()) {
				if (imageType.getImageType().equals(productImage.getType())) {
					imageTypeValidator = true;
				}
			}

			if (!imageTypeValidator) {
				System.out.println("TYPE ENUM EXCEPTION");
				return false;
			}
		}
		return true;
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
