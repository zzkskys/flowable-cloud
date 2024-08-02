package org.example.flowablecloud

import org.flowable.engine.delegate.DelegateExecution
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/02
 * @author zzk
 */
@Component
class ScheduleDemo {

    companion object {
        private val log = LoggerFactory.getLogger(ScheduleDemo::class.java)
    }

    fun print(execution: DelegateExecution) {
        log.info("定时任务启动,processInstanceId: ${execution.processInstanceId}")
    }
}