package kr.or.connect.resv.manager.dto;

public class InputProductPriceDto {

	// save to product_price Table with product_id
	String priceTypeName;
	Integer price;
	Double discountRate; // Decimal(5,2)

	public String getPriceTypeName() {
		return priceTypeName;
	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	@Override
	public String toString() {
		return "InputProductPriceDto [priceTypeName=" + priceTypeName + ", price=" + price + ", discountRate=" + discountRate + "]";
	}

}
