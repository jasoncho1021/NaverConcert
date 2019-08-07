package kr.or.connect.resv.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.config.ApplicationConfig;
import kr.or.connect.resv.dto.model.Product;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class ReservationManagerDaoTest {

	@Autowired
	private ReservationManagerDao dao;
	
	@Test
	public void shouldSelect() {
		//given
		
		//when
		
		//then
		List<Product> productList = dao.selectLimitProducts(0);
		assertThat(productList.size(), is(4));
	}

}
