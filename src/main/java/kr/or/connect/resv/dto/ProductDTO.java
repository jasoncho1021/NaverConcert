package kr.or.connect.resv.dto;

import java.util.List;
import kr.or.connect.resv.dto.model.Product;

public class ProductDTO {

	private List<Product> items;
	private int totalCount;

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

}
