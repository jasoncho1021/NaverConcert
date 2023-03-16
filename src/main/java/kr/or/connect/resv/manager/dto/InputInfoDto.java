package kr.or.connect.resv.manager.dto;

import java.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

public class InputInfoDto {
	// from detail page
	// save to product Table
	InputProductDto inputProductDto;

	InputImageDto[] productImages;
	MultipartFile mapImage;

	// from reservation page
	// save to display_info Table with product_id
	InputDisplayInfoDto inputDisplayInfoDto;

	InputProductPriceDto[] inputProductPriceDto;

	public InputImageDto[] getProductImages() {
		return productImages;
	}

	public void setProductImages(InputImageDto[] productImages) {
		this.productImages = productImages;
	}

	public InputProductDto getInputProductDto() {
		return inputProductDto;
	}

	public void setInputProductDto(InputProductDto inputProductDto) {
		this.inputProductDto = inputProductDto;
	}

	public MultipartFile getMapImage() {
		return mapImage;
	}

	public void setMapImage(MultipartFile mapImage) {
		this.mapImage = mapImage;
	}

	public InputDisplayInfoDto getInputDisplayInfoDto() {
		return inputDisplayInfoDto;
	}

	public void setInputDisplayInfoDto(InputDisplayInfoDto inputDisplayInfoDto) {
		this.inputDisplayInfoDto = inputDisplayInfoDto;
	}

	public InputProductPriceDto[] getInputProductPriceDto() {
		return inputProductPriceDto;
	}

	public void setInputProductPriceDto(InputProductPriceDto[] inputProductPriceDto) {
		this.inputProductPriceDto = inputProductPriceDto;
	}

	@Override
	public String toString() {
		return "InputInfoDto [inputProductDto=" + inputProductDto + ", productImages=" + Arrays.toString(productImages)
				+ ", mapImage=" + mapImage + ", inputDisplayInfoDto=" + inputDisplayInfoDto + ", inputProductPriceDto="
				+ Arrays.toString(inputProductPriceDto) + "]";
	}

}
