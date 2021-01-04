package kr.or.connect.resv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.connect.resv.service.ReservationService;

@Controller
public class ViewController {
	private static final String ATTRIBUTE_NAME = "RESERVATION_EMAIL";

	@Autowired
	private ReservationService reservationService;

	@GetMapping(path = "/")
	public String getMain(HttpSession session) {
		session.removeAttribute(ATTRIBUTE_NAME);
		return "forward:/mainpage";
	}

	@GetMapping(path = "/mainpage")
	public String getMainPage() {
		return "mainpage";
	}

	@GetMapping(path = "/detail")
	public String getDetailPage() {
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

	@GetMapping(path = "/reviewWrite")
	public String getReviewWritePage(HttpServletRequest request, ModelMap model) {
		model.addAttribute("productId", request.getParameter("productId"));
		model.addAttribute("reservationInfoId", request.getParameter("reservationInfoId"));
		model.addAttribute("productDescription", request.getParameter("productDescription"));

		return "reviewWrite";
	}

	@GetMapping(path = "/login")
	public String login(@RequestParam(name = "reservationEmail", required = false) String reservationEmail,
			HttpSession session, ModelMap model) {

		String sessionReservationEmail = (String) session.getAttribute(ATTRIBUTE_NAME);
		if (sessionReservationEmail != null) {
			return "myreservation";
		}

		if (reservationEmail == null) {
			return "redirect:/bookinglogin";
		}

		if (reservationService.getReservations(reservationEmail).getSize() > 0) {
			session.setAttribute(ATTRIBUTE_NAME, reservationEmail);
			return "myreservation";
		}

		return "redirect:/bookinglogin";
	}

}
