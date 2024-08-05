package org.example.flowablecloud

import org.flowable.engine.delegate.BpmnError
import org.flowable.engine.delegate.DelegateExecution
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/05
 * @author zzk
 */
@Component("healthCode")
class HealthyCodeService {

    companion object {

        const val HEALTHY_CODE = "healthyCodeStatus"

        private val log = LoggerFactory.getLogger(HealthyCodeService::class.java)
    }

    /**
     * 自动审批
     */
    fun autoAudit(execution: DelegateExecution) {
        val code = execution.getVariable(HEALTHY_CODE) as String
        if ("green" != code) {
            val errorCode = "HealthyCodeNotGreen"
            log.error("健康码异常, code:$errorCode")

            throw BpmnError("10001", errorCode)
        }
    }
}