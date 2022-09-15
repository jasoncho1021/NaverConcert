package kr.or.connect.resv.manager.dto;

public class InputProductDto {

	String description;
	String content;
	String event;
	Integer categoryId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "InputProductDto [description=" + description + ", content=" + content + ", event=" + event
				+ ", categoryId=" + categoryId + "]";
	}

}
