package com.ftd.bpm.listener;

import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

public class EscalatePOEventListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariable("accepted", true);
    }
}
