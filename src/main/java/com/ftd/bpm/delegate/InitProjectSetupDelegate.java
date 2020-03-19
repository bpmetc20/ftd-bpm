package com.ftd.bpm.delegate;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.DelegateHelper;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.context.Context;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class InitProjectSetupDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        log.info("=====InitProjectSetupDelegate======");
    }
}
