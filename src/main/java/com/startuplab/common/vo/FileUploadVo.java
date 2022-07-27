package com.startuplab.common.vo;

import java.io.File;
import lombok.Data;

@Data
public class FileUploadVo {
	private String fileName;
	private String fileUrl;
	private File file;
}
