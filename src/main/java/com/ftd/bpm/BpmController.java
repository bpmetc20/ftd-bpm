package com.ftd.bpm;

import lombok.extern.log4j.Log4j2;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class BpmController {
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(path = "/ftd-api/process")
    public String startProcess() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("customerId", "1");
        vars.put("accepted", false);
        vars.put("negotiated", false);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ftd-project", vars);
        log.info("created process instance " + processInstance.getId());
        return processInstance.getDeploymentId();
    }

    @GetMapping(path = "/ftd-api/process/active")
    public String getActiveProcesses() {
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
        return !processInstances.isEmpty()? processInstances.get(0).getDeploymentId() : "-1";
    }

    @GetMapping(path = "/ftd-api/{deploymentId}/task/complete")
    public String completeActiveTask(@PathVariable("deploymentId") String deploymentId) {
        List<Task> tasks = taskService.createTaskQuery().deploymentId(deploymentId).list();
        String result;
        if ( !tasks.isEmpty() ) {
            taskService.claim(tasks.get(0).getId(), "bpmuser");
            taskService.complete(tasks.get(0).getId());
            result = "OK";
        } else {
            result = "not found";
        }
        return result;
    }
}
