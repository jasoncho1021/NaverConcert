package kr.or.connect.resv.service.impl;
/**
 * https://www.boostcourse.org/web316/project/11/content/9#review/4723/code
 */

import java.util.List;
import kr.or.connect.resv.dao.ReservationDao;
import kr.or.connect.resv.dto.CommentResponse;
import kr.or.connect.resv.dto.ReservationInfoResponse;
import kr.or.connect.resv.dto.ReservationResponse;
import kr.or.connect.resv.dto.model.ReservationInfo;
import kr.or.connect.resv.dto.model.ReservationParam;
import kr.or.connect.resv.dto.model.ReservationPrice;
import kr.or.connect.resv.service.ReservationService;
import kr.or.connect.resv.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Override
    @Transactional(readOnly = true)
    public ReservationInfoResponse getNestedReservations(String reservationEmail) {
        ReservationInfoResponse reservationInfoResponse = new ReservationInfoResponse();
        List<ReservationInfo> reservations = reservationDao.selectNestedReservationInfoByReservationEmail(
                reservationEmail);
        reservationInfoResponse.setReservations(reservations);
        reservationInfoResponse.setSize(reservations.size());
        return reservationInfoResponse;
    }

    @Override
    @Transactional
    public Integer makeReservation(ReservationParam reservationParam) {
        Integer reservationInfoId = reservationDao.insertReservationInfo(reservationParam);

        for (ReservationPrice reservationPrice : reservationParam.getPrices()) {
            reservationPrice.setReservationInfoId(reservationInfoId);
            reservationDao.insertReservationInfoPrice(reservationPrice);
        }
        return reservationInfoId;
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse makeReservationResponse(Integer reservationInfoId) {
        ReservationResponse reservationResponse = reservationDao.selectReservationResponseById(reservationInfoId);
        List<ReservationPrice> reservationPrices = reservationDao.selectReservationPricesById(reservationInfoId);
        reservationResponse.setPrices(reservationPrices);
        return reservationResponse;
    }

    @Override
    @Transactional
    public ReservationResponse cancelReservation(Integer reservationInfoId) {
        reservationDao.cancelReservationInfo(reservationInfoId);
        return makeReservationResponse(reservationInfoId);
    }

    @Override
    @Transactional
    public Integer makeReservationComment(CommentResponse requestParams, MultipartFile attachedImage) {
        int reservationUserCommentId = reservationDao.insertReservationUserComment(requestParams);
        System.out.println(attachedImage.getContentType());
        System.out.println(Util.isValidImageType(attachedImage.getContentType()));
        if (attachedImage != null && !attachedImage.isEmpty() && Util.isValidImageType(
                attachedImage.getContentType())) {
            requestParams.setCommentId(reservationUserCommentId);
            makeReservationCommentImage(requestParams, attachedImage);
        }

        return reservationUserCommentId;
    }

    private Integer makeReservationCommentImage(CommentResponse requestParams, MultipartFile attachedImage) {
        return saveReservationUserCommentImage(requestParams, saveImageFile(attachedImage));
    }

    private Integer saveReservationUserCommentImage(CommentResponse requestParams, Integer fileInfoId) {
        return reservationDao.insertReservationUserCommentImage(requestParams, fileInfoId);
    }

    private Integer saveImageFile(MultipartFile attachedImage) {
        String uniqueFileName = Util.saveImageFile(attachedImage);
        return reservationDao.insertFileInfo(uniqueFileName
                , Util.COMMENT_IMG_PATH + uniqueFileName
                , attachedImage.getContentType());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponse getCommentResponse(Integer reservationUserCommentId) {
        CommentResponse commentResponse = reservationDao.selectReservationCommentByCommentId(reservationUserCommentId);
        commentResponse.setCommentImage(
                reservationDao.selectReservationCommentImageByCommentId(reservationUserCommentId));
        return commentResponse;
    }
}
