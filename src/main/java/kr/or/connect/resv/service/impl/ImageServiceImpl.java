package kr.or.connect.resv.service.impl;

import kr.or.connect.resv.dao.ImageDao;
import kr.or.connect.resv.dto.model.ImageInfo;
import kr.or.connect.resv.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageDao imageDao;

	@Override
	public ImageInfo getImageByProductId(Integer productId) {
		return imageDao.getImageInfoByProductId(productId);
	}

	@Override
	public ImageInfo getImageByImageId(Integer imageId) {
		return imageDao.getImageInfoByImageId(imageId);
	}

	@Override
	public ImageInfo getImageByReservationUserCommentImageId(Integer reservationUserCommentImageId) {
		return imageDao.getImageInfoByReservationUserCommentImageId(reservationUserCommentImageId);
	}
}
