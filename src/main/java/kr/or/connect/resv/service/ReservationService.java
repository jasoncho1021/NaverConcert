package kr.or.connect.resv.service;

import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.resv.dto.CommentResponse;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.ReservationParam;

public interface ReservationService {
	public ReservationInfoResponse getReservations(String reservationEmail);
	public Integer makeReservation(ReservationParam reservationParam);
	public ReservationResponse cancelReservation(Integer reservationId);
	public ReservationResponse makeReservationResponse(Integer reservationInfoId);
	public Integer makeReservationComment(CommentResponse requestParams, MultipartFile attachedImage);
	public CommentResponse getCommentResponse(Integer reservationUserCommentId);
}
