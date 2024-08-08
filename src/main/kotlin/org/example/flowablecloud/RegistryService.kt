package org.example.flowablecloud

import org.flowable.engine.delegate.DelegateExecution
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/08
 * @author zzk
 */
@Component("registryService")
class RegistryService {

    companion object {
        private val log = LoggerFactory.getLogger(RegistryService::class.java)
    }

    /**
     * 取消正式报名
     */
    fun cancelRegister(execution: DelegateExecution) {
        log.info("取消正式报名,processInstanceId: ${execution.processInstanceId}")
    }


    /**
     * 取消预报名
     */
    fun cancelPreRegister(execution: DelegateExecution) {
        log.info("取消预报名,processInstanceId: ${execution.processInstanceId}")
    }


}