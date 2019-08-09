package kr.or.connect.resv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

	private static final String ATTRIBUTE_NAME = "RESERVATION_EMAIL";

/*	@GetMapping(path = "/")
	public String getMain() {
		return "redirect:/mainpage";
	}*/

	@GetMapping(path = "/detail")
	public String getDetailPage(HttpSession session, ModelMap model) {
		setSessionAttribute(session, model);
		System.out.println("디테일");
		return "detail";
	}

	@GetMapping(path = "/mainpage")
	public String getMainPage(HttpSession session, ModelMap model) {
		setSessionAttribute(session, model);
		System.out.println("메인");
		return "mainpage";
	}

	private void setSessionAttribute(HttpSession session, ModelMap model) {
		String reservationEmail = (String) session.getAttribute(ATTRIBUTE_NAME);
		if (reservationEmail != null) {
			model.addAttribute("reservationEmail", reservationEmail);
			System.out.println(reservationEmail);
		} else {
			System.out.println("널이라네!");
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
		System.out.println("북로그인");
		return "bookinglogin";
	}

/*	@GetMapping(path = "/myreservation")
	public String getMyReservationPage(HttpSession session, ModelMap model) {
		setSessionAttribute(session, model);
		System.out.println("마이레져");
		return "myreservation";
	}*/

	@GetMapping(path = "/login")
	public String login(@RequestParam(name = "reservationEmail", required = false) String reservationEmail,
			HttpSession session,
			ModelMap model,
			RedirectAttributes redirectAttr) {
		/*
		  if( "kimjinsu@connect.co.kr".equals(reservationEmail) ) {
		 		System.out.println("로그인통과!");
		 		session.setAttribute(ATTRIBUTE_NAME, reservationEmail);
		 		model.addAttribute("reservationEmail", reservationEmail);
				System.out.println("마이레져");
				return "redirect:/myreservation";
		   }
		   	
		   	System.out.println("null 예약정보 없음");
			return "redirect:/bookinglogin";
		 */
		
		if (reservationEmail == null) {
			redirectAttr.addFlashAttribute("errorMessage", "예약 정보가 없습니다.");
			System.out.println("null 예약정보 없음");
			return "redirect:/bookinglogin";
		} else if (reservationEmail.equals("kimjinsu@connect.co.kr")) { // 1개 이상 결과값 있으면
			session.setAttribute(ATTRIBUTE_NAME, reservationEmail);
		} else {
			System.out.println("일치하는 예약정보 없음");
			return "redirect:/bookinglogin";
		}
		System.out.println("로그인통과!");
		setSessionAttribute(session, model);
		System.out.println("마이레져");
		return "redirect:/myreservation";
	}

}
