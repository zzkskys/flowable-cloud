package org.example.flowablecloud

import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/13
 * @author zzk
 */
@Component("cancelMeeting")
class CancelMeeting : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(CancelMeeting::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        log.info("会议取消")

    }
}