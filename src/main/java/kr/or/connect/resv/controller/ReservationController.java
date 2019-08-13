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
		return reservationService.makeReservation(reservationParam);
	}

	@PutMapping("/{reservationId}")
	public ReservationResponse cancelReservation(@PathVariable Integer reservationId) {
		return reservationService.cancelReservation(reservationId);
	}

}
