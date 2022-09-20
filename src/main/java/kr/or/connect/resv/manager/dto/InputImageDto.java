package kr.or.connect.resv.manager.dto;

import org.springframework.web.multipart.MultipartFile;

public class InputImageDto {
	// save to file_info and then product_image with (prodcut_id, file_id)Tables
	String imageType;
	MultipartFile productImage;

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	@Override
	public String toString() {
		return "ImageInfo [imageType=" + imageType + ", productImage=" + productImage + "]";
	}

}
