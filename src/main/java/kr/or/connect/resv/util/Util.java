package kr.or.connect.resv.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class Util {
	public static final String IMG_ROOT_PATH;
	public static final String COMMENT_IMG_PATH = "img_comment/";
	public static final String IMAGE_TYPE = "th";
	public static final String[] IMAGE_TYPES = {"image/jpg", "image/png"};

	static
	{
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
	}

	public static String saveImageFile(MultipartFile attachedImage) {
		String fileName = attachedImage.getOriginalFilename();
		String uploadPath = IMG_ROOT_PATH + COMMENT_IMG_PATH;
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
				saveFileName = saveFileName + "_" + System.currentTimeMillis();
			}
		}
		return saveFileName;
	}

	public static boolean isValidImageType(String imageType) {
		return Arrays.asList(IMAGE_TYPES).contains(imageType);
	}
}
