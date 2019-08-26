package kr.or.connect.resv.service;

import kr.or.connect.resv.dto.model.ImageInfo;

public interface ImageService {
	public ImageInfo getImageByProductId(Integer productId);
	public ImageInfo getImageByImageId(Integer imageId);
	public ImageInfo getImageByReservationUserCommentImageId(Integer reservationUserCommentImageId);
}
