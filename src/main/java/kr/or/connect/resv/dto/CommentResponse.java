package kr.or.connect.resv.dto;

import java.time.LocalDateTime;
import kr.or.connect.resv.dto.model.CommentImage;
import org.springframework.format.annotation.DateTimeFormat;

public class CommentResponse {

	private String comment;
	private Integer commentId;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime modifyDate;
	private Integer productId;
	private Integer reservationInfoId;
	private Integer score;
	private CommentImage commentImage;

	@Override
	public String toString() {
		return "CommentResponse [comment=" + comment + ", commentId=" + commentId + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", productId=" + productId + ", reservationInfoId=" + reservationInfoId
				+ ", score=" + score + ", commentImage=" + commentImage + "]";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(Integer reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public CommentImage getCommentImage() {
		return commentImage;
	}

	public void setCommentImage(CommentImage commentImage) {
		this.commentImage = commentImage;
	}

}
