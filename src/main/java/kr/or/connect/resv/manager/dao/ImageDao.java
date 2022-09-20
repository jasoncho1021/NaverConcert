package kr.or.connect.resv.manager.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.ProductImage;

/*
insert into file_info (id, file_name, save_file_name, content_type, delete_flag, create_date, modify_date ) 
values ( 60,'1_th_1.png', 'img/1_th_1.png', 'image/png', 0, now(), now());
*/
//insert into product_image (id, product_id, type, file_id) values ( 129,50, 'ma', '188');
//insert into display_info_image (id, display_info_id, file_id) values ( 3, 3, 3);
@Repository("ImageDao")
public class ImageDao {
	private SimpleJdbcInsert insertFileInfo;
	private SimpleJdbcInsert insertProductImage;
	private SimpleJdbcInsert insertDisplayInfoImage;

	public ImageDao(DataSource dataSource) {
		this.insertFileInfo = new SimpleJdbcInsert(dataSource).withTableName("file_info")
				.usingGeneratedKeyColumns("id");
		this.insertProductImage = new SimpleJdbcInsert(dataSource).withTableName("product_image")
				.usingGeneratedKeyColumns("id");
		this.insertDisplayInfoImage = new SimpleJdbcInsert(dataSource).withTableName("display_info_image")
				.usingGeneratedKeyColumns("id");
	}

	public Integer insertFileInfo(ProductImage productImage, LocalDateTime currentTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("file_name", productImage.getFileName());
		params.put("save_file_name", productImage.getSaveFileName());
		params.put("content_type", productImage.getContentType());
		params.put("delete_flag", productImage.getDeleteFlag());
		params.put("create_date", currentTime);
		params.put("modify_date", currentTime);

		return insertFileInfo.executeAndReturnKey(params).intValue();
	}

	public Integer insertProductImage(ProductImage productImage) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", productImage.getProductId());
		params.put("type", productImage.getType());
		params.put("file_id", productImage.getFileInfoId());
		return insertProductImage.executeAndReturnKey(params).intValue();
	}

	public Integer insertDisplayInfoImage(DisplayInfoImage displayInfoImage) {
		Map<String, Object> params = new HashMap<>();
		params.put("display_info_id", displayInfoImage.getDisplayInfoId());
		params.put("file_id", displayInfoImage.getFileId());
		return insertDisplayInfoImage.executeAndReturnKey(params).intValue();
	}
}
