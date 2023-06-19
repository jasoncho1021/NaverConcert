package kr.or.connect.resv.dto.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ReservationInfo {
	private boolean cancelYn;
	private String createDate;
	private DisplayInfo displayInfo;
	private int displayInfoId;
	private String modifyDate;
	private int productId;
	private String reservationDate;
	private String reservationEmail;
	private int reservationInfoId;
	private String reservationName;
	private String reservationTelephone;
	private int totalPrice;
}
