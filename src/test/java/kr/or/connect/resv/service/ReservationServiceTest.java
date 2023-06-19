package kr.or.connect.resv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import kr.or.connect.resv.dto.ReservationInfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Test
    void serviceTest() {
        String reservationEmail = "carami@connect.co.kr";
        ReservationInfoResponse response = reservationService.getNestedReservations(reservationEmail);
        int actual = response.getReservations().get(0).getTotalPrice();
        int expected = 6000;
        assertEquals(expected, actual);
        //ReservationInfoResponse expected = reservationService.getReservations(reservationEmail);
        //assertEquals(expected, actual);
    }
}
