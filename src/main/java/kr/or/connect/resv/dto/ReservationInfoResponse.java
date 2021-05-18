package kr.or.connect.resv.dto;

import java.util.List;

import kr.or.connect.resv.dto.model.ReservationInfo;

public class ReservationInfoResponse {

	@Override
	public String toString() {
		return "ReservationInfoResponse [reservations=" + reservations.size() + ", size=" + size + "]";
	}

	private List<ReservationInfo> reservations;
	private int size;

	public List<ReservationInfo> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationInfo> reservations) {
		this.reservations = reservations;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
