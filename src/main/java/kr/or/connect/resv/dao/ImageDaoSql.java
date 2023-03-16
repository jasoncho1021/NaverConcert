package kr.or.connect.resv.dao;

public class ImageDaoSql {
	public static final String SELECT_IMAGE_BY_PRODUCT_ID="select fi.save_file_name, fi.content_type " + 
			"from " + 
			"product_image pi " + 
			"join " + 
			"file_info fi " + 
			"on pi.product_id = :productId and pi.type = :imgtype and pi.file_id = fi.id";
	public static final String SELECT_IMAGE_BY_IMAGE_ID="select fi.save_file_name, fi.content_type " + 
			"from " + 
			"file_info fi " + 
			"where " + 
			"fi.id = :imageId";
	public static final String SELECT_IMAGE_BY_RESERVATION_USER_COMMENT_IMAGE_ID="select fi.save_file_name, fi.content_type " + 
			"from " + 
			"reservation_user_comment_image ri " + 
			"join " + 
			"file_info fi " + 
			"on " + 
			"ri.id = :reservationUserCommentImageId and ri.file_id = fi.id";
}
