package kr.or.connect.resv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.service.ReservationService;
import kr.or.connect.resv.util.Keywords;

@Controller
@SessionAttributes(Keywords.ATTRIBUTE_NAME)
public class ViewController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReservationService reservationService;

	@GetMapping(path = "/")
	public String getRootPage(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		//session.removeAttribute(Keywords.ATTRIBUTE_NAME);
		return "forward:/mainpage";
		//return "redirect:/mainpage";
	}

	@GetMapping(path = "/mainpage")
	public String getMainPage() {
		return "mainpage";
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

	@GetMapping(path = "/login")
	public String login(@RequestParam(name = Keywords.ATTRIBUTE_NAME, required = true) String reservationEmail,
			HttpSession session, ModelMap model) {

		ReservationInfoResponse reservationInfoResponse = reservationService.getReservations(reservationEmail);
		if (reservationInfoResponse.getSize() > 0) {
			model.addAttribute("userVO", reservationInfoResponse);
			model.addAttribute(Keywords.ATTRIBUTE_NAME, reservationEmail);
		}

		return "myreservation";
	}

}
