package com.startuplab.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.startuplab.common.util.CUtil;
import com.startuplab.common.util.ImageUtil;
import com.startuplab.common.util.S3Util;
import com.startuplab.common.vo.FileUploadVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUploadService {
  public static String upload_file_root = "/DATA/temp/files";
  private S3Util s3;

  @PostConstruct
  public void init() {

    // gson = CValue.getGson();
    try {
      s3 = new S3Util();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public FileUploadVo uploader(MultipartFile file, int image_resize_length) {
    FileUploadVo vo = null;
    try {
      if (file == null || file.isEmpty()) {
        return vo;
      }
      // log.info("{}", file.getContentType());
      String originalFilename = file.getOriginalFilename();
      String suffix = CUtil.getFileExtension(originalFilename);
      String fileName = "/" + CUtil.getUuid();
      fileName += (CUtil.isEmptyString(suffix)) ? "" : "." + suffix;
      String subDir = "/upload";
      String fileUrl = subDir + fileName;
      File folder = new File(upload_file_root + subDir);
      if (!folder.exists()) {
        try {
          folder.mkdirs(); // 폴더 생성합니다.
        } catch (Exception e) {
          log.error(e.getMessage());
          e.printStackTrace();
        }
      }
      Path path = Paths.get(upload_file_root, subDir, fileName);
      file.transferTo(path);
      File newFile = path.toFile();
      if (image_resize_length > 0) {
        ImageUtil iu = new ImageUtil();
        newFile = iu.ImageResize(newFile, image_resize_length);
      }
      vo = new FileUploadVo();
      String uploadUrl = s3.uploadFile(newFile, fileUrl);
      vo.setFileName(originalFilename);
      vo.setFileUrl(uploadUrl);
      // newFile.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return vo;
  }

  public FileUploadVo uploader(MultipartFile file) {
    return uploader(file, 0);
  }

  public List<FileUploadVo> uploader(MultipartFile[] files, int image_resize_length) {
    List<FileUploadVo> list = new ArrayList<>();
    if (files != null && files.length > 0) {
      for (MultipartFile file : files) {
        FileUploadVo vo = uploader(file, image_resize_length);
        if (vo != null)
          list.add(vo);
      }
    }
    return list;
  }

  public List<FileUploadVo> uploader(MultipartFile[] files) {
    return uploader(files, 0);
  }

  @Async
  public void deleteFile(String url) {
    s3.deleteFile(url);
  }
}
