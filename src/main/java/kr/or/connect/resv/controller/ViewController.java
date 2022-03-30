package kr.or.connect.resv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.service.ReservationService;
import kr.or.connect.resv.util.Keywords;

@Controller
public class ViewController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping(path = "/")
	public String getRootPage(HttpSession session) {
		session.removeAttribute(Keywords.AUTHENTICATION_KEY);
		return "forward:/mainpage";
	}

	@GetMapping(path = "/mainpage")
	public String getMainPage() {
		return "mainpage";
	}

	@GetMapping(path = "/manager")
	public String getManagerPage() {
		return "manager/manager";
	}

	@GetMapping(path = "/uploadDetail")
	public String getUploadDetail() {
		return "manager/uploadDetail";
	}

	@GetMapping(path = "/uploadReserve")
	public String getUploadReserve() {
		return "manager/uploadReserve";
	}

	@GetMapping(path = "/detail")
	public String getDetailPage(@RequestParam(name = "id", required = true) String id) {
		return "detail";
	}

	@GetMapping(path = "/review")
	public String getReviewPage() {
		return "review";
	}

	@GetMapping(path = "/reserve")
	public String getReservePage() {
		return "reserve";
	}

	@GetMapping(path = "/bookinglogin")
	public String getBookingLoginPage() {
		return "bookinglogin";
	}

	@GetMapping(path = "/myreservation")
	public String getMyReservationPage() {
		return "myreservation";
	}

	@GetMapping(path = "/reviewWrite")
	public String getReviewWritePage(HttpServletRequest request, ModelMap model) {
		model.addAttribute("productId", request.getParameter("productId"));
		model.addAttribute("reservationInfoId", request.getParameter("reservationInfoId"));
		model.addAttribute("productDescription", request.getParameter("productDescription"));

		return "reviewWrite";
	}

	@GetMapping(path = "/userlogin")
	public String login(@RequestParam(name = Keywords.AUTHENTICATION_KEY, required = true) String reservationEmail,
			HttpSession session, ModelMap model) {

		ReservationInfoResponse reservationInfoResponse = reservationService.getReservations(reservationEmail);
		if (reservationInfoResponse.getSize() > 0) {
			model.addAttribute(Keywords.USER_DATA, reservationInfoResponse);
			model.addAttribute(Keywords.AUTHENTICATION_KEY, reservationEmail);
		}

		return "myreservation";
	}

}
