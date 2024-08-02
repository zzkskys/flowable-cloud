package org.example.flowablecloud

import org.flowable.engine.delegate.DelegateExecution
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


/**
 *
 * @since : 2024/08/02
 * @author zzk
 */
@Component("Logger")
class LoggerPrint {

    companion object {
        private val log = LoggerFactory.getLogger(LoggerPrint::class.java)
    }

    fun log(execution: DelegateExecution, message: String) {
        execution.variables.forEach { (name, value) ->
            log.info("name: $name,value: $value")
        }
    }
}