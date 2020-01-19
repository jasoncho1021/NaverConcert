package kr.or.connect.resv.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.resv.dto.CommentResponse;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.service.ReservationService;

@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@GetMapping
	public LocalDateTime getReservationDate() {
		Random random = new Random();
		int randomScope = random.nextInt(5);
		LocalDateTime reservationDate = LocalDateTime.now().plusDays(randomScope);
		return reservationDate;
	}

	@GetMapping(params = "reservationEmail")
	public ReservationInfoResponse getReservations(
			@RequestParam(value = "reservationEmail", required = true) String reservationEmail) {
		ReservationInfoResponse reservationInfoResponse = reservationService.getReservations(reservationEmail);
		return reservationInfoResponse;
	}

	@PostMapping
	public ReservationResponse makeReservation(@RequestBody ReservationParam reservationParam) {
		System.out.println("날짜: " + reservationParam.getReservationYearMonthDay());
		int reservationInfoId = reservationService.makeReservation(reservationParam);
		return reservationService.makeReservationResponse(reservationInfoId);
	}

	@PutMapping("/{reservationId}")
	public ReservationResponse cancelReservation(@PathVariable Integer reservationId) {
		return reservationService.cancelReservation(reservationId);
	}

	@PostMapping("/{reservationInfoId}/comments")
	public CommentResponse makeReservationComment(@RequestParam(value = "comment", required = true) String comment,
			@RequestParam(value = "productId", required = true) Integer productId,
			@RequestParam(value = "score", required = true) Integer score,
			@RequestParam("attachedImage") MultipartFile attachedImage, @PathVariable Integer reservationInfoId) {

		CommentResponse requestParams = new CommentResponse();
		requestParams.setProductId(productId);
		requestParams.setScore(score);
		requestParams.setComment(comment);
		requestParams.setReservationInfoId(reservationInfoId);

		int reservationUserCommentId = reservationService.makeReservationComment(requestParams, attachedImage);

		return reservationService.getCommentResponse(reservationUserCommentId);
	}
}
