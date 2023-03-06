package kr.or.connect.resv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.resv.dto.model.ImageInfo;
import kr.or.connect.resv.service.ImageService;
import kr.or.connect.resv.util.Util;

@RestController
@RequestMapping(path = "/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/product/{productId}")
    public void downloadProductImageByProductId(HttpServletResponse response, @PathVariable Integer productId) {
        downloadImage(response, imageService.getImageByProductId(productId));
    }

    @GetMapping("/{imageId}")
    public void downloadImageByImageId(HttpServletResponse response, @PathVariable Integer imageId) {
        downloadImage(response, imageService.getImageByImageId(imageId));
    }

    @GetMapping("/comment/{reservationUserCommentImageId}")
    public void downloadReservationUserCommentImageByCommentId(HttpServletResponse response,
            @PathVariable Integer reservationUserCommentImageId) {
        downloadImage(response, imageService.getImageByReservationUserCommentImageId(reservationUserCommentImageId));
    }

    private void downloadImage(HttpServletResponse response, ImageInfo imageInfo) {
        String saveFileName = imageInfo.getSaveFileName();
        String savePath = Util.IMG_ROOT_PATH + saveFileName;
        String contentType = imageInfo.getContentType();

        File file = new File(savePath);
        int fileLength = (int) file.length();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + saveFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");

        try (FileInputStream fis = new FileInputStream(file);
                OutputStream out = response.getOutputStream();) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            return;
        }
    }
}
