package kr.or.connect.resv.manager.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.resv.manager.dto.InputProductDto;
import kr.or.connect.resv.manager.dto.InputProductPriceDto;

/*
insert into product (id, description, content, event, category_id, create_date, modify_date) 
values (
50, 
'세대공감 음악극 [사는게 꽃같네] ',
'가까이 있으면서도 무심했던 우리 가족의 모습을 더 들여다보고 싶게 하는 이야기 세대공감음악극 [사는게 꽃같네]', 
'', 
5, 
now(), 
now());

*/

//insert into product_price (id, product_id, price_type_name , price, discount_rate, create_date, modify_date) values ( 1,1, 'A',6000,20, now(), now());
@Repository
public class ProductDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SimpleJdbcInsert insertProduct;
	private SimpleJdbcInsert insertProductPrice;

	public ProductDao(DataSource dataSource) {
		this.insertProduct = new SimpleJdbcInsert(dataSource).withTableName("product").usingGeneratedKeyColumns("id");
		this.insertProductPrice = new SimpleJdbcInsert(dataSource).withTableName("product_price")
				.usingGeneratedKeyColumns("id");
	}

	public Integer insertProduct(InputProductDto inputProductDto, LocalDateTime currentTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("description", inputProductDto.getDescription());
		params.put("content", inputProductDto.getContent());
		params.put("event", inputProductDto.getEvent());
		params.put("category_id", inputProductDto.getCategoryId());
		params.put("create_date", currentTime);
		params.put("modify_date", currentTime);
		
		logger.debug("--> params {}", params);

		return insertProduct.executeAndReturnKey(params).intValue();
	}

	public Integer insertProductPrice(Integer productId, InputProductPriceDto inputProductPriceDto,
			LocalDateTime currentTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", productId);
		params.put("price_type_name", inputProductPriceDto.getPriceTypeName());
		params.put("price", inputProductPriceDto.getPrice());
		params.put("discount_rate", inputProductPriceDto.getDiscountRate());
		params.put("create_date", currentTime);
		params.put("modify_date", currentTime);

		return insertProductPrice.executeAndReturnKey(params).intValue();
	}

}
