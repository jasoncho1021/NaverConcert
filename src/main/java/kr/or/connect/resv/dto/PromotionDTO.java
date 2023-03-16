package kr.or.connect.resv.dto;

import java.util.List;
import kr.or.connect.resv.dto.model.Promotion;

public class PromotionDTO {

	private List<Promotion> items;

	public List<Promotion> getItems() {
		return items;
	}

	public void setItems(List<Promotion> items) {
		this.items = items;
	}

}
