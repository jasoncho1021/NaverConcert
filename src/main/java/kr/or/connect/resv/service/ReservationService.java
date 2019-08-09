package kr.or.connect.resv.service;

import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.ReservationParam;

public interface ReservationService {
	public ReservationInfoResponse getReservations(String reservationEmail);
	public ReservationResponse makeReservation(ReservationParam reservationParam);
	public ReservationResponse cancelReservation(Integer reservationId);
}
