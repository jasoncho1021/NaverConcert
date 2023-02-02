package kr.or.connect.resv.dao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional	;
import org.springframework.web.context.WebApplicationContext;

import kr.or.connect.resv.config.ApplicationConfig;
import kr.or.connect.resv.config.WebMvcContextConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class, WebMvcContextConfiguration.class })
@WebAppConfiguration
@Transactional
public class UploadTest {

	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac).alwaysDo(print(System.out)).build();
	}

	public void testHomeUrl(int displayInfoId) {
		try {
			this.mvc.perform(get("/api/products/{displayInfoId}", displayInfoId)).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@WithMockUser(roles = "MANAGER")
	@Test
	public void context() {
		// given
		MockMultipartFile productImage1 = new MockMultipartFile("productImages[0].productImage", "test1.PNG",
				MediaType.IMAGE_PNG_VALUE, "test1".getBytes());
		MockMultipartFile productImage2 = new MockMultipartFile("productImages[1].productImage", "test2.jpg",
				MediaType.IMAGE_JPEG_VALUE, "test2".getBytes());
		MockMultipartFile productImage3 = new MockMultipartFile("productImages[2].productImage", "test3.jpg",
				MediaType.IMAGE_JPEG_VALUE, "test3".getBytes());
		MockMultipartFile mapImage = new MockMultipartFile("mapImage", "map.PNG", MediaType.IMAGE_PNG_VALUE,
				"map".getBytes());

		// when, then
		try {
			MvcResult result = mvc.perform(multipart("/manager/upload").file(productImage1).file(productImage2)
					.file(productImage3).file(mapImage).param("productImages[0].imageType", "th")
					.param("productImages[1].imageType", "ma").param("productImages[2].imageType", "ma")
					.param("inputProductDto.description", "타이").param("inputProductDto.content", "a")
					.param("inputProductDto.event", "b").param("inputProductDto.categoryId", "2")
					.param("inputDisplayInfoDto.openingHours", "c").param("inputDisplayInfoDto.placeName", "d")
					.param("inputDisplayInfoDto.placeLot", "그").param("inputDisplayInfoDto.placeStreet", "팩")
					.param("inputDisplayInfoDto.tel", "e").param("inputDisplayInfoDto.homepage", "f")
					.param("inputDisplayInfoDto.email", "g").param("inputProductPriceDto[0].priceTypeName", "C")
					.param("inputProductPriceDto[0].price", "1000").param("inputProductPriceDto[0].discountRate", "10")
					.param("inputProductPriceDto[1].priceTypeName", "S").param("inputProductPriceDto[1].price", "2000")
					.param("inputProductPriceDto[1].discountRate", "20")).andExpect(status().isCreated()).andReturn();

			int displayInfoId = Integer.valueOf(result.getResponse().getContentAsString());
			testHomeUrl(displayInfoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
