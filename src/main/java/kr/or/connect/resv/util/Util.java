package kr.or.connect.resv.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class Util {
	public static final String IMG_ROOT_PATH;
	public static final String COMMENT_IMG_PATH = "img_comment/";
	public static final String UPLOADED_IMG_PATH = "img/";//"img_uploaded/";
	public static final String UPLOADED_MAP_IMG_PATH = "img_map/";//"img_map_uploaded/";

	public static final String IMAGE_TYPE = "th";
	public static final String[] IMAGE_TYPES = { "image/jpg", "image/png" };

	static {
		String operSys = System.getProperty("os.name").toLowerCase();
		if (operSys.contains("win")) {
			IMG_ROOT_PATH = "c:/tmp/";
		} else {
			IMG_ROOT_PATH = "/tmp/";
		}

		File dir = new File(IMG_ROOT_PATH + COMMENT_IMG_PATH);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		File udir = new File(IMG_ROOT_PATH + UPLOADED_IMG_PATH);
		if (!udir.isDirectory()) {
			udir.mkdirs();
		}

		File umdir = new File(IMG_ROOT_PATH + UPLOADED_MAP_IMG_PATH);
		if (!umdir.isDirectory()) {
			umdir.mkdirs();
		}
	}

	public static String saveMapImageFile(Integer productId, MultipartFile attachedImage) {
		String contentType = attachedImage.getContentType();
		String uploadPath = IMG_ROOT_PATH + UPLOADED_MAP_IMG_PATH;
		StringBuilder fileName = new StringBuilder(productId + "");
		fileName.append("_");
		fileName.append("map");
		fileName.append("_");
		fileName.append(productId + "");
		fileName.append(".");
		fileName.append(contentType.substring(contentType.indexOf("/") + 1));
		return saveFile(attachedImage, uploadPath, fileName.toString());
	}

	public static String saveProductImageFile(Integer productId, MultipartFile attachedImage, String imageType,
			Integer index) {
		String contentType = attachedImage.getContentType();
		String uploadPath = IMG_ROOT_PATH + UPLOADED_IMG_PATH;
		StringBuilder fileName = new StringBuilder(productId + "");
		fileName.append("_");
		fileName.append(imageType);
		fileName.append("_");
		fileName.append(index);
		fileName.append(".");
		fileName.append(contentType.substring(contentType.indexOf("/") + 1));
		return saveFile(attachedImage, uploadPath, fileName.toString());
	}

	private static String saveFile(MultipartFile attachedImage, String uploadPath, String fileName) {
		try {
			attachedImage.transferTo(new File(uploadPath + fileName));
			return fileName;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException("file Save Error");
		}
	}

	public static String saveUploadedFile(MultipartFile attachedImage) {
		String uploadPath = IMG_ROOT_PATH + UPLOADED_IMG_PATH;
		return save(attachedImage, uploadPath);
	}

	public static String saveImageFile(MultipartFile attachedImage) {
		String uploadPath = IMG_ROOT_PATH + COMMENT_IMG_PATH;
		return save(attachedImage, uploadPath);
	}

	private static String save(MultipartFile attachedImage, String uploadPath) {
		String fileName = attachedImage.getOriginalFilename();

		String uniqueFileName = getValidSaveFileName(uploadPath, fileName);

		try {
			attachedImage.transferTo(new File(uploadPath + uniqueFileName));
			return uniqueFileName;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException("file Save Error");
		}
	}

	private static String getValidSaveFileName(String uploadPath, String saveFileName) {
		if (saveFileName != null && !saveFileName.equals("")) {
			if (new File(uploadPath + saveFileName).exists()) {
				saveFileName = System.currentTimeMillis() + "_" + saveFileName;
			}
		}
		return saveFileName;
	}

	public static boolean isValidImageType(String imageType) {
		return Arrays.asList(IMAGE_TYPES).contains(imageType);
	}
}
