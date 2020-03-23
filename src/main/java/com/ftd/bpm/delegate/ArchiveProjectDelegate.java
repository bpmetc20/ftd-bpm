package com.ftd.bpm.delegate;

import lombok.extern.log4j.Log4j2;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

@Log4j2
public class ArchiveProjectDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        log.info("======ArchiveProjectDelegate=======");
    }
}
