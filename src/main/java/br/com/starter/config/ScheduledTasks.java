package br.com.starter.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import br.com.starter.admin.entity.Job;
import br.com.starter.admin.service.JobService;

@Component
@EnableScheduling
public class ScheduledTasks {

	private static Map<Job, ScheduledFuture<?>> schedules = new HashMap<>();

	@Autowired
	private JobService jobService;

	public void scheduleTask(Job job) {

		schedules.put(job, new ConcurrentTaskScheduler().schedule(new Runnable() {

			@Override
			public void run() {

				jobService.start(job.getId());

			}
		}, new Trigger() {

			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				switch (job.getUnidadeTempo()) {
				case SEGUNDOS:
					return Date.from(
							LocalDateTime.now().plusSeconds(job.getTempo()).atZone(ZoneId.systemDefault()).toInstant());

				case MINUTOS:
					return Date.from(
							LocalDateTime.now().plusMinutes(job.getTempo()).atZone(ZoneId.systemDefault()).toInstant());

				case HORAS:
					return Date.from(
							LocalDateTime.now().plusHours(job.getTempo()).atZone(ZoneId.systemDefault()).toInstant());

				case DIAS:
					return Date.from(
							LocalDateTime.now().plusDays(job.getTempo()).atZone(ZoneId.systemDefault()).toInstant());

				default:
					return null;
				}
			}
		}));
	}

	public static void stop(Job job) {
		ScheduledFuture<?> scheduledFuture = schedules.get(job);
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
			schedules.remove(job);
		}
	}

}
