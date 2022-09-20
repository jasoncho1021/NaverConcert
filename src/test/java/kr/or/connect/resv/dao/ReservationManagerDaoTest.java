package kr.or.connect.resv.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.config.ApplicationConfig;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.ReservationInfo;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.dto.model.ReservationPrice;
import kr.or.connect.resv.service.ReservationService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class ReservationManagerDaoTest {

	@Autowired
	private ReservationManagerDao dao;

	@Autowired
	private ReservationDao rDao;

	@Autowired
	private ReservationService service;

	@Test
	public void insertReservationParamGetReservationResponse() {
		// given
		ReservationParam param = new ReservationParam();

		List<ReservationPrice> prices = new ArrayList<ReservationPrice>();

		ReservationPrice priceA = new ReservationPrice();
		priceA.setCount(2);
		priceA.setProductPriceId(1);

		ReservationPrice priceB = new ReservationPrice();
		priceB.setCount(3);
		priceB.setProductPriceId(3);

		prices.add(priceA);
		prices.add(priceB);

		param.setPrices(prices);
		param.setDisplayInfoId(1);
		param.setProductId(1);
		param.setReservationEmail("huni@naver.com");
		param.setReservationName("조재");
		param.setReservationTelephone("0100-3333-2222");

		String dateTime = "2019-04-16T15:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		param.setReservationYearMonthDay(LocalDateTime.parse(dateTime, formatter));
		System.out.println(LocalDateTime.parse(dateTime, formatter));

		// when
		int reservationInfoId = rDao.insertReservationInfo(param);
		System.out.println(reservationInfoId);

		//reservationInfoId = 22;

		for (ReservationPrice reservationPrice : param.getPrices()) {
			reservationPrice.setReservationInfoId(reservationInfoId);

			int reservationInfoPriceId = rDao.insertReservationInfoPrice(reservationPrice);
			reservationPrice.setReservationInfoPriceId(reservationInfoPriceId);
		}

		ReservationResponse reservationResponse = rDao.selectReservationResponseById(reservationInfoId);
		List<ReservationPrice> reservationPrices = rDao.selectReservationPricesById(reservationInfoId);

		reservationResponse.setPrices(reservationPrices);
		// then
		// assertThat(param, is(16));
		System.out.println(reservationResponse);
		ReservationInfoResponse reservationInfoResponse = reservationInfoByEmail(param.getReservationEmail());
		System.out.println(reservationInfoResponse);
	}

	public ReservationInfoResponse reservationInfoByEmail(String reservationEmail) {
		ReservationInfoResponse reservationInfoResponse = new ReservationInfoResponse();
		List<ReservationInfo> reservations = rDao.selectReservationInfoByReservationEmail(reservationEmail);

		for (ReservationInfo reservationInfo : reservations) {
			DisplayInfo displayInfo = rDao.selectDisplayInfoByDisplayInfoId(reservationInfo.getDisplayInfoId());

			int totalPrice = rDao.selectTotalPriceByReservationInfoId(reservationInfo.getReservationInfoId());
			reservationInfo.setDisplayInfo(displayInfo);
			reservationInfo.setTotalPrice(totalPrice);
		}

		reservationInfoResponse.setReservations(reservations);
		reservationInfoResponse.setSize(reservations.size());

		return reservationInfoResponse;
	}

}
