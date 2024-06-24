package com.kh.spring.common.scheduling;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduleController {


	// @Scheduled(fixedDelay = 1000)
	public void test() {
		log.debug("1초마다 출력");

	}

	// @Scheduled(cron = "0/1 * * * * *")
	public void testCron() {
		log.debug("크론탭방식 스케줄링");
	}
}

