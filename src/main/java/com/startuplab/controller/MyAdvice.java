
package com.startuplab.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.startuplab.common.exception.MyException;
import com.startuplab.common.exception.MyException.MyError;
import com.startuplab.common.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class MyAdvice {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  @ResponseBody
  public ApiResult handleMaxSizeException(Exception exception) {
    ApiResult result = new ApiResult();
    try {
      log.error("MaxUploadSizeExceededException: {}", exception.getMessage());
      /*
       * maxFileSize: 파일 하나 최대 사이즈
       * maxRequestSize: 요청 전체의 사이즈 
       * 
       * 둘중 하나라도 오버되면 여기서 처리됨. 메세지가 조금 다름
       * maxRequestSize를 먼저 확인함.
       */
      throw new MyException(MyError.MAXIMUM_UPLOAD_SIZE_EXCEED);
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      e.printStackTrace();
      result.setMyError();
    }

    return result;
  }

  @ExceptionHandler(InvalidFormatException.class)
  @ResponseBody
  public ApiResult handleIllegalArgumentException(Exception exception) {
    ApiResult result = new ApiResult();
    try {
      log.error("InvalidFormatException: {}", exception.getMessage());
      throw new MyException(MyError.INVALID_FORMAT);
    } catch (MyException e) {
      result.setMyError(e);
    } catch (Exception e) {
      e.printStackTrace();
      result.setMyError();
    }
    return result;
  }
}
