package kr.or.connect.resv.manager.controller;

import java.util.Arrays;
import kr.or.connect.resv.manager.dto.InputInfoDto;
import kr.or.connect.resv.manager.service.UploadService;
import kr.or.connect.resv.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/uploadmanager")
public class UploadProductInfoController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UploadService uploadService;

	public UploadProductInfoController(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@PostMapping("/upload")
	public ResponseEntity<Integer> upload(InputInfoDto inputInfoDto) {
		logger.debug("==> input {}", inputInfoDto);
		
		int id = uploadService.uploadProduct(inputInfoDto);

		logger.debug("--> 생성된 ID {}", id);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	// 생성된 ID 반환해야함.
	@PostMapping("/uploadtest")
	public ResponseEntity uploadTest(InputInfoDto productInfoDTO) {
		System.out.println(productInfoDTO);

		MultipartFile mapImage = productInfoDTO.getMapImage();
		System.out.println("지도 파일 이름 : " + mapImage.getOriginalFilename());
		System.out.println("지도 파일 크기 : " + mapImage.getSize());
		System.out.println("지도 파일 형식 : " + mapImage.getContentType());
		System.out.println("저장된 지도 파일 경로 : " + Util.UPLOADED_IMG_PATH + Util.saveUploadedFile(mapImage));

		try {
			Arrays.asList(productInfoDTO.getProductImages()).stream().forEach(imageInfo -> {
				MultipartFile file = imageInfo.getProductImage();
				System.out.println("파일 이름 : " + file.getOriginalFilename());
				System.out.println("파일 크기 : " + file.getSize());
				System.out.println("파일 형식 : " + file.getContentType());
				System.out.println("이미지 타입 : " + imageInfo.getImageType());

				String uniqueFileName = Util.saveUploadedFile(file);
				System.out.println("저장된 파일 경로 : " + Util.UPLOADED_IMG_PATH + uniqueFileName);
			});
			return new ResponseEntity(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
