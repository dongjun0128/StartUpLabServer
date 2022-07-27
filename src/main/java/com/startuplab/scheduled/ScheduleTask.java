package com.startuplab.scheduled;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleTask {
	// @Autowired
	// private CommonService common;

	@PostConstruct
	public void init() {
		try {

		} catch (Exception e) {
			log.error("{}", e.getMessage());
			e.printStackTrace();
		}
	}
	// @Scheduled(cron="0 31 * * * *") //초 분 시 일 월 요일
	// @Scheduled(fixedDelay = 1000)
	// public void ScheduleTask() throws Exception {
	// }
}
