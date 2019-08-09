package kr.or.connect.resv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.resv.dao.ReservationManagerDao;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.DisplayInfo;
import kr.or.connect.resv.dto.model.ReservationInfo;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.dto.model.ReservationPrice;
import kr.or.connect.resv.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationManagerDao reservationManagerDao;

	@Override
	@Transactional(readOnly = true)
	public ReservationInfoResponse getReservations(String reservationEmail) {
		ReservationInfoResponse reservationInfoResponse = new ReservationInfoResponse();
		List<ReservationInfo> reservations = reservationManagerDao.selectReservationInfoByReservationEmail(reservationEmail);

		for (ReservationInfo reservationInfo : reservations) {
			DisplayInfo displayInfo = reservationManagerDao.selectDisplayInfoByDisplayInfoId(reservationInfo.getDisplayInfoId());
			int totalPrice = reservationManagerDao.selectTotalPriceByReservationInfoId(reservationInfo.getReservationInfoId());
			reservationInfo.setDisplayInfo(displayInfo);
			reservationInfo.setTotalPrice(totalPrice);
		}

		reservationInfoResponse.setReservations(reservations);
		reservationInfoResponse.setSize(reservations.size());
		return reservationInfoResponse;
	}

	@Override
	@Transactional
	public ReservationResponse makeReservation(ReservationParam reservationParam) {
		int reservationInfoId = reservationManagerDao.insertReservationInfo(reservationParam);

		for (ReservationPrice reservationPrice : reservationParam.getPrices()) {
			reservationPrice.setReservationInfoId(reservationInfoId);
			int reservationInfoPriceId = reservationManagerDao.insertReservationInfoPrice(reservationPrice);
			//reservationPrice.setReservationInfoPriceId(reservationInfoPriceId);
		}

		ReservationResponse reservationResponse = reservationManagerDao.selectReservationResponseById(reservationInfoId);
		List<ReservationPrice> reservationPrices = reservationManagerDao.selectReservationPricesById(reservationInfoId);

		reservationResponse.setPrices(reservationPrices);
		return reservationResponse;
	}

	@Override
	@Transactional
	public ReservationResponse cancelReservation(Integer reservationId) {
		// TODO Auto-generated method stub
		return null;
	}
}
