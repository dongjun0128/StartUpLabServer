package com.startuplab.common.util;

import java.io.File;
import org.apache.tika.Tika;
import com.startuplab.common.vo.Image;
import com.startuplab.common.vo.ImageLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtil {
	public Boolean isImageFile(File f) {
		Boolean result = false;
		try {
			String mimeType = CUtil.getFileMineType(f);
			if (mimeType.startsWith("image")) {
				result = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	public File ImageResize(File f, int length) {
		File file = null;
		Image img = null;
		Image resized = null;
		try {
			if (isImageFile(f)) {
				img = ImageLoader.fromFile(f);
				log.debug("Image '" + img.getSourceType() + "' dimensions: {} x {} ", img.getWidth(), img.getHeight());
				log.debug("Size: {}", f.length());
				resized = img.getResized(length);
				if (resized != null) {
					log.debug("Resized Image '" + resized.getSourceType() + "' dimensions: {} x {} ", resized.getWidth(), resized.getHeight());
					file = resized.writeToFile(f);
					log.debug("Size: {}", file.length());
				} else {
					log.warn("resized is null");
					file = f;
				}
			} else {
				file = f;
				log.warn("Because it is not an image file, I did not resize it.");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return file;
	}

	public File ImageResize(String filePath, int length) {
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				throw new IllegalArgumentException("There is no file in that path.");
			}
			f = ImageResize(f, length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	public void testimage() {
		String imgLocation = "/DATA/test/profile.jpg";
		try {
			File f = new File(imgLocation);
			Tika tika = new Tika();
			String mimeType = tika.detect(f);
			log.error("mimeType: {}", mimeType);
			File nf = ImageResize(f, 640);
			String mimeType1 = tika.detect(nf);
			log.error("new mimeType: {}", mimeType1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
