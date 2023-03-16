package kr.or.connect.resv.manager.service.impl;

import java.time.LocalDateTime;
import kr.or.connect.resv.dto.model.DisplayInfoImage;
import kr.or.connect.resv.dto.model.ProductImage;
import kr.or.connect.resv.manager.dao.DisplayInfoDao;
import kr.or.connect.resv.manager.dao.ImageDao;
import kr.or.connect.resv.manager.dao.ProductDao;
import kr.or.connect.resv.manager.dto.InputDisplayInfoDto;
import kr.or.connect.resv.manager.dto.InputImageDto;
import kr.or.connect.resv.manager.dto.InputInfoDto;
import kr.or.connect.resv.manager.dto.InputProductDto;
import kr.or.connect.resv.manager.dto.InputProductPriceDto;
import kr.or.connect.resv.manager.service.UploadService;
import kr.or.connect.resv.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ProductDao productDao;
	private final DisplayInfoDao displayInfoDao;
	private final ImageDao imageDao;

	public UploadServiceImpl(ProductDao productDao, DisplayInfoDao displayInfoDao, ImageDao imageDao) {
		this.productDao = productDao;
		this.displayInfoDao = displayInfoDao;
		this.imageDao = imageDao;
	}

	@Transactional
	@Override
	public Integer uploadProduct(InputInfoDto inputInfoDto) {
		LocalDateTime currentTime = LocalDateTime.now();

		logger.debug("--> service input {}", inputInfoDto);
		Integer productId = saveProduct(inputInfoDto.getInputProductDto(), currentTime);
		logger.debug("==> productId {}", productId);

		for (InputProductPriceDto inputProductPriceDto : inputInfoDto.getInputProductPriceDto()) {
			saveProductPrice(productId, inputProductPriceDto, currentTime);
		}

		saveProductImage(productId, inputInfoDto.getProductImages(), currentTime);

		Integer displayInfoId = saveDisplayInfo(productId, inputInfoDto.getInputDisplayInfoDto(), currentTime);
		saveDisplayInfoImage(productId, displayInfoId, inputInfoDto.getMapImage(), currentTime);

		//return productId;
		return displayInfoId;
	}

	private Integer saveProduct(InputProductDto inputProductDto, LocalDateTime currentTime) {
		return productDao.insertProduct(inputProductDto, currentTime);
	}

	private Integer saveProductPrice(Integer productId, InputProductPriceDto inputProductPriceDto,
			LocalDateTime currentTime) {
		return productDao.insertProductPrice(productId, inputProductPriceDto, currentTime);
	}

	private Integer saveDisplayInfo(Integer productId, InputDisplayInfoDto inputDisplayInfoDto,
			LocalDateTime currentTime) {
		return displayInfoDao.insertDisplayInfo(productId, inputDisplayInfoDto, currentTime);
	}

	private void saveProductImage(Integer productId, InputImageDto[] inputImageDtos, LocalDateTime currentTime) {
		InputImageDto inputImageDto;
		for (int i = 0; i < inputImageDtos.length; i++) {
			inputImageDto = inputImageDtos[i];
			MultipartFile file = inputImageDto.getProductImage();

			String imageType = inputImageDto.getImageType();
			System.out.println("파일 형식 : " + file.getContentType());
			System.out.println("이미지 타입 : " + imageType);

			String fileName = Util.saveProductImageFile(productId, file, imageType, i + 1);
			System.out.println("저장된 파일 경로 : " + Util.UPLOADED_IMG_PATH + fileName);

			ProductImage productImage = new ProductImage();
			productImage.setFileName(fileName);
			productImage.setSaveFileName(Util.UPLOADED_IMG_PATH + fileName);
			productImage.setContentType(file.getContentType());

			int fileInfoId = imageDao.insertFileInfo(productImage, currentTime);
			productImage.setFileInfoId(fileInfoId);
			productImage.setProductId(productId);
			productImage.setType(imageType);

			imageDao.insertProductImage(productImage);
		}
	}

	private Integer saveDisplayInfoImage(Integer productId, Integer displayInfoId, MultipartFile mapImage,
			LocalDateTime currentTime) {
		DisplayInfoImage displayInfoImage = new DisplayInfoImage();
		String fileName = Util.saveMapImageFile(productId, mapImage);
		ProductImage productImage = new ProductImage();
		productImage.setFileName(fileName);
		productImage.setSaveFileName(Util.UPLOADED_MAP_IMG_PATH + fileName);
		productImage.setContentType(mapImage.getContentType());

		int fileInfoId = imageDao.insertFileInfo(productImage, currentTime);
		displayInfoImage.setFileId(fileInfoId);
		displayInfoImage.setDisplayInfoId(displayInfoId);
		return imageDao.insertDisplayInfoImage(displayInfoImage);
	}
}
