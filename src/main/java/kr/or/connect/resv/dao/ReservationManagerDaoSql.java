package kr.or.connect.resv.dao;

public class ReservationManagerDaoSql {
	public static final String SELECT_LIMIT_PRODUCTS="select p.id as product_id, p.description as product_description, p.content as product_content, d.place_name, d.id as display_info_id " + 
													"from " + 
													"product p " + 
													"join " +
													"display_info d on p.id = d.product_id " + 
													"order by p.id limit :start, :limit";
	public static final String SELECT_LIMIT_CATEGORY_PRODUCTS="select p.id as product_id, p.description as product_description, p.content as product_content, d.place_name, d.id as display_info_id " + 
															"from " + 
															"product p " + 
															"join " + 
															"display_info d on p.id = d.product_id and p.category_id = :categoryId " +
															"order by p.id limit :start, :limit";
	public static final String SELECT_FILENAME="select fi.save_file_name " + 
												"from " + 
												"product_image pi " + 
												"join " + 
												"file_info fi " + 
												"on pi.product_id = :productId and pi.type = :imgtype and pi.file_id = fi.id";
	public static final String SELECT_PRODUCT_IMAGE_FILENAMES="select fi.content_type, fi.create_date, fi.delete_flag, fi.id as file_info_id, fi.file_name, fi.modify_date, pi.product_id, pi.id as product_image_id, fi.save_file_name, pi.type " + 
															"from " + 
															"product_image pi " + 
															"join " + 
															"file_info fi " + 
															"on pi.product_id = :id and pi.file_id = fi.id and pi.type in ('ma', 'et') " +
															"order by pi.type desc";
	public static final String SELECT_CATEGORY_COUNT="select count(*) " + 
													"from " + 
													"product p " + 
													"join " + 
													"display_info di on p.id = di.product_id and p.category_id = :categoryId";
	public static final String SELECT_CATEGORIES="select c.id, c.name, count(*) as count " + 
												"from " + 
												"product p " + 
												"join " +
												"display_info d on p.id = d.product_id " +
												"join " +
												"category c on c.id = p.category_id " + 
												"group by c.id";
	public static final String SELECT_ALL_PROMOTIONS="select pm.id, pm.product_id " + 
													"from " + 
													"promotion pm " + 
													"join " + 
													"product p on pm.product_id = p.id";
	public static final String SELECT_COMMENTS_BY_DISPLAY_INFO_ID="select rc.comment, rc.id as comment_id, rc.create_date, rc.modify_date, ri.product_id, ri.reservation_date, ri.reservation_email, rc.reservation_info_id, ri.reservation_name, ri.reservation_tel as reservation_telephone, rc.score " +
																"from " +
																"reservation_info ri " +
																"join " +
																"reservation_user_comment rc on ri.display_info_id = :displayInfoId " +
																"and ri.id = rc.reservation_info_id";
	public static final String SELECT_COMMENT_IMAGES_BY_DISPLAY_INFO_ID="select fi.content_type, fi.create_date, fi.delete_flag, fi.id as file_id, fi.file_name, rci.id as image_id, fi.modify_date, rci.reservation_info_id, rci.reservation_user_comment_id, fi.save_file_name " +
																"from " +
																"reservation_info ri " +
																"join " +
																"reservation_user_comment_image rci on ri.display_info_id = :displayInfoId " +
																"and rci.reservation_info_id = ri.id " +
																"join " +
																"file_info fi on rci.file_id = fi.id";
	public static final String SELECT_DISPLAY_INFO_BY_DISPLAY_INFO_ID="select p.category_id, c.name as category_name, di.create_date, di.id as display_info_id, di.email, di.homepage, di.modify_date, di.opening_hours, di.place_lot, di.place_name, di.place_street, p.content as product_content, p.description as product_description, p.event as product_event, di.product_id, di.tel as telephone " + 
																"from " +
																"display_info di " +
																"join " +
																"product p on di.product_id = p.id and di.id = :displayInfoId " +
																"join " +
																"category c on c.id = p.category_id";
	public static final String SELECT_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID="select fi.content_type, fi.create_date, fi.delete_flag, dii.display_info_id, dii.id as display_info_image_id, dii.file_id, fi.file_name, fi.modify_date, fi.save_file_name " +
																"from " +
																"display_info_image dii " +
																"join " +
																"file_info fi on dii.display_info_id = :displayInfoId " +
																"and dii.file_id = fi.id";
	public static final String SELECT_PRODUCT_PRICES="select create_date, discount_rate, modify_date, price, price_type_name, product_id, id as product_price_id " + 
													"from " + 
													"product_price " + 
													"where " + 
													"product_id = :productId";
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
}
