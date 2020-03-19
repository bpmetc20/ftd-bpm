package com.ftd.bpm.listener;

import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

public class NegotiatePOEventListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariable("negotiated", true);
    }
}
