package kr.or.connect.resv.dao;

public class ReservationDaoSql {
	public static final String SELECT_RESERVATION_COMMENT_IMAGE_BY_COMMENT_ID="select " + 
																			"fi.content_type, " + 
																			"fi.create_date, " + 
																			"fi.modify_date, " + 
																			"fi.delete_flag, " + 
																			"ci.file_id, " + 
																			"fi.file_name, " + 
																			"ci.id as image_id, " + 
																			"ci.reservation_info_id, " + 
																			"ci.reservation_user_comment_id, " + 
																			"fi.save_file_name " + 
																			"from " + 
																			"reservation_user_comment_image ci " + 
																			"join " + 
																			"file_info fi " + 
																			"on " + 
																			"fi.id = ci.file_id and ci.reservation_user_comment_id = :commentId";
	public static final String SELECT_RESERVATION_COMMENT_BY_COMMENT_ID="select " + 
																		"comment, " + 
																		"id as comment_id, " + 
																		"create_date, " + 
																		"modify_date, " + 
																		"product_id, " + 
																		"reservation_info_id, " + 
																		"score " + 
																		"from " + 
																		"reservation_user_comment " + 
																		"where " + 
																		"id = :commentId";
	public static final String UPDATE_RESERVAION_INFO_CANCEL="update reservation_info set cancel_flag = 1, modify_date = now() where id = :reservationInfoId";
	public static final String SELECT_RESERVATION_RESPONSE_BY_RESERVATION_INFO_ID="select " + 
																				"cancel_flag as cancel_yn, " + 
																				"create_date, " + 
																				"display_info_id, " + 
																				"modify_date, " + 
																				"product_id, " + 
																				"reservation_date, " + 
																				"reservation_email, " + 
																				"id as reservation_info_id, " + 
																				"reservation_name, " + 
																				"reservation_tel as reservation_telephone " + 
																				"from " + 
																				"reservation_info " + 
																				"where " + 
																				"id = :reservationInfoId";
	public static final String SELECT_RESERVATION_PRICE_BY_RESERVATION_INFO_ID="select count, product_price_id, id as reservation_info_price_id, reservation_info_id " + 
																				"from " + 
																				"reservation_info_price " + 
																				"where " + 
																				"reservation_info_id = :reservationInfoId";
	public static final String SELECT_TOTAL_PRICE_BY_RESERVATION_INFO_ID="select sum(pp.price * rip.count) as totalprice " + 
																		"from reservation_info_price rip " + 
																		"join product_price pp on rip.product_price_id = pp.id and rip.reservation_info_id = :reservationInfoId";
	public static final String SELECT_RESERVATION_INFO_BY_RESERVATION_EMAIL="select " + 
																			"ri.cancel_flag as cancel_yn, ri.create_date, ri.display_info_id, " + 
																			"ri.modify_date, ri.product_id, ri.reservation_date, " + 
																			"ri.reservation_email, ri.id as reservation_info_id, ri.reservation_name, " + 
																			"ri.reservation_tel as reservation_telephone " + 
																			"from reservation_info ri " + 
																			"where " + 
																			"ri.reservation_email = :reservationEmail";
}
