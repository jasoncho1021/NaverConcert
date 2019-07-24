package kr.or.connect.resv.dto.model;

public enum ImageType {
	ma("ma"), th("th"), et("et");

	final private String imageType;

	private ImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageType() {
		return imageType;
	}
}
