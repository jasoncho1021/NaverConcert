package kr.or.connect.resv.manager.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.manager.dto.InputDisplayInfoDto;

/*
insert into display_info (id, 
product_id, 
opening_hours, 
place_name, 
place_lot, 
place_street, 
tel, 
homepage, 
email,
create_date,
modify_date ) 

values ( 
59,
50, 
'2017.12.20(수)~21.(목) 16:00, 20:00', 
'경성대학교 예노소극장', 
'부산광역시 남구 대연동 산108-5', 
'부산광역시 남구 수영로 309 경성대학교', 
'1688-8998', 
'http://mcong.kr',
 '', 
 now(), 
 now()
 );
*/
@Repository
public class DisplayInfoDao {
	private SimpleJdbcInsert insertDisplayInfo;

	public DisplayInfoDao(DataSource dataSource) {
		this.insertDisplayInfo = new SimpleJdbcInsert(dataSource).withTableName("display_info")
				.usingGeneratedKeyColumns("id");
	}

	public Integer insertDisplayInfo(Integer productId, InputDisplayInfoDto inputDisplayInfoDto,
			LocalDateTime currentTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", productId);
		params.put("opening_hours", inputDisplayInfoDto.getOpeningHours());
		params.put("place_name", inputDisplayInfoDto.getPlaceName());
		params.put("place_lot", inputDisplayInfoDto.getPlaceLot());
		params.put("place_street", inputDisplayInfoDto.getPlaceStreet());
		params.put("tel", inputDisplayInfoDto.getTel());
		params.put("homepage", inputDisplayInfoDto.getHomepage());
		params.put("email", inputDisplayInfoDto.getEmail());
		params.put("create_date", currentTime);
		params.put("modify_date", currentTime);

		return insertDisplayInfo.executeAndReturnKey(params).intValue();
	}
}
