package kr.or.connect.resv.service;

import kr.or.connect.resv.dto.CommentResponse;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.ReservationParam;
import org.springframework.web.multipart.MultipartFile;

public interface ReservationService {
	ReservationInfoResponse getNestedReservations(String reservationEmail);
	Integer makeReservation(ReservationParam reservationParam);
	ReservationResponse cancelReservation(Integer reservationId);
	ReservationResponse makeReservationResponse(Integer reservationInfoId);
	Integer makeReservationComment(CommentResponse requestParams, MultipartFile attachedImage);
	CommentResponse getCommentResponse(Integer reservationUserCommentId);
}
