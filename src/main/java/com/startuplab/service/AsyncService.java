package com.startuplab.service;

import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncService {

  @PostConstruct
  public void init() {
    try {

    } catch (Exception e) {
      log.error("{}", e.getMessage());
    }
  }

  @Async
  public void asyncTest() {
    log.error("Thread Async: {}", Thread.currentThread().getId());
    try {
      Thread.sleep(3000); // 3초 대기
      log.error("end");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
