package com.startuplab;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.startuplab.common.CValue;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.startuplab")
public class Application {

	@PostConstruct
	public void started() {
		// timezone 셋팅
		TimeZone.setDefault(TimeZone.getTimeZone(CValue.SERVER_TIME_ZONE));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
