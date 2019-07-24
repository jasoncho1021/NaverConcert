package kr.or.connect.resv.dto;

import java.util.List;

import kr.or.connect.resv.dto.model.Product;

public class ProductDTO {

	private List<Product> productLimitList;
	private int totalCount;

	public void setProductLimitList(List<Product> productLimitList) {
		this.productLimitList = productLimitList;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<Product> getProductLimitList() {
		return productLimitList;
	}

	public int getTotalCount() {
		return totalCount;
	}

}
