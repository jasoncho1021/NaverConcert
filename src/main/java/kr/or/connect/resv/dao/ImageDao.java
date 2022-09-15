package kr.or.connect.resv.dao;

import static kr.or.connect.resv.dao.ImageDaoSql.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.dto.model.ImageInfo;
import kr.or.connect.resv.util.Util;

@Repository("ImageDaoReader")
public class ImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ImageInfo> imageInfoRowMapper = BeanPropertyRowMapper.newInstance(ImageInfo.class);

	public ImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public ImageInfo getImageInfoByProductId(Integer productId) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		params.put("imgtype", Util.IMAGE_TYPE);
		return jdbc.queryForObject(SELECT_IMAGE_BY_PRODUCT_ID, params, imageInfoRowMapper);
	}

	public ImageInfo getImageInfoByImageId(Integer imageId) {
		Map<String, Object> param = new HashMap<>();
		param.put("imageId", imageId);
		return jdbc.queryForObject(SELECT_IMAGE_BY_IMAGE_ID, param, imageInfoRowMapper);
	}
	
	public ImageInfo getImageInfoByReservationUserCommentImageId(Integer reservationUserCommentImageId) {
		Map<String, Object> param = new HashMap<>();
		param.put("reservationUserCommentImageId", reservationUserCommentImageId);
		return jdbc.queryForObject(SELECT_IMAGE_BY_RESERVATION_USER_COMMENT_IMAGE_ID, param, imageInfoRowMapper);
	}
}
