package com.ftd.bpm;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.RepositoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log4j2
public class BpmApplication {
	private BpmController bpmController;


	public static void main(String[] args) {
		SpringApplication.run(BpmApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService,
								  final RuntimeService runtimeService,
								  final TaskService taskService) {

		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				bpmController = new BpmController();

				bpmController.setRepositoryService(repositoryService);
				bpmController.setRuntimeService(runtimeService);
				bpmController.setTaskService(taskService);

				log.info("Number of process definitions : "
						+ repositoryService.createProcessDefinitionQuery().count());
				log.info("Number of tasks : " + taskService.createTaskQuery().count());

				bpmController.startProcess();

				log.info("Number of tasks after process start: "
						+ taskService.createTaskQuery().count());

				log.info("Active processes: " + bpmController.getActiveProcesses());

				log.info("Complete active task: " + bpmController.completeActiveTask());

				log.info("Number of tasks after process start: "
						+ taskService.createTaskQuery().count());

			}
		};
	}
}

