package kr.or.connect.resv.controller;

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
	public String getMain() {
		return "forward:/mainpage";
	}

	@GetMapping(path = "/detail")
	public String getDetailPage(HttpSession session, ModelMap model) {
		setSessionAttribute(session, model);
		return "detail";
	}

	@GetMapping(path = "/mainpage")
	public String getMainPage(HttpSession session, ModelMap model) {
		setSessionAttribute(session, model);
		return "mainpage";
	}

	private void setSessionAttribute(HttpSession session, ModelMap model) {
		String reservationEmail = (String) session.getAttribute(ATTRIBUTE_NAME);
		if (reservationEmail != null) {
			model.addAttribute("reservationEmail", reservationEmail);
		}
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
	public String getBookingLoginPage(HttpSession session) {
		return "bookinglogin";
	}

	@GetMapping(path = "/login")
	public String login(@RequestParam(name = "reservationEmail", required = false) String reservationEmail,
			HttpSession session, ModelMap model) {

		String sessionReservationEmail = (String) session.getAttribute(ATTRIBUTE_NAME);
		if (sessionReservationEmail != null) {
			setSessionAttribute(session, model);
			return "myreservation";
		}

		if (reservationEmail == null) {
			return "redirect:/bookinglogin";
		}

		if (reservationService.getReservations(reservationEmail).getSize() > 0) {
			session.setAttribute(ATTRIBUTE_NAME, reservationEmail);
			setSessionAttribute(session, model);
			return "myreservation";
		}

		return "redirect:/bookinglogin";
	}

}
