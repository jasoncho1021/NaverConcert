package kr.or.connect.resv.dao;

public class ResvmanagerDaoSql {
	public static final String SELECT_ALL_PRODUCTS="select p.id, p.description, p.content, d.place_name " + 
													"from " + 
													"product p " + 
													"join " + 
													"display_info d on p.id = d.product_id " + 
													"order by p.id";
	public static final String SELECT_ONE_PRODUCT="select p.id, p.description, p.content, d.place_name " + 
													"from " + 
													"product p " + 
													"join " + 
													"display_info d on p.id = d.product_id and d.product_id = :productId"; 
	public static final String SELECT_LIMIT_PRODUCTS="select p.id, p.description, p.content, d.place_name, d.id as display_info_id " + 
													"from " + 
													"product p " + 
													"join " +
													"display_info d on p.id = d.product_id " + 
													"order by p.id limit :start, :limit";
	public static final String SELECT_PRODUCTS_COUNT="select count(*) from product";
	public static final String SELECT_LIMIT_CATEGORY_PRODUCTS="select p.id, p.description, p.content, d.place_name, d.id as display_info_id " + 
															"from " + 
															"product p " + 
															"join " + 
															"display_info d on p.id = d.product_id and p.category_id = :categoryId " +
															"order by p.id limit :start, :limit";
	public static final String SELECT_FILENAME="select fi.file_name " + 
												"from " + 
												"product_image pi " + 
												"join " + 
												"file_info fi " + 
												"on pi.product_id = :id and pi.type = :imgtype and pi.file_id = fi.id";
	public static final String SELECT_CATEGORIES="select c.id, c.name, count(*) as count " + 
												"from " + 
												"product p " + 
												"join " +
												"display_info d on p.id = d.product_id " +
												"join " +
												"category c on c.id = p.category_id " + 
												"group by c.id";
	public static final String SELECT_CATEGORIES_COUNT="select count(*) from category";
	public static final String SELECT_ALL_PROMOTIONS="select pm.id, pm.product_id, p.category_id, ca.name as category_name, p.description, pi.id as product_image_id " + 
													"from " + 
													"promotion pm " + 
													"join " + 
													"product p on pm.product_id = p.id " + 
													"join " + 
													"product_image pi on p.id = pi.product_id and pi.type=\"ma\" " + 
													"join " + 
													"category ca on p.category_id = ca.id";
}
