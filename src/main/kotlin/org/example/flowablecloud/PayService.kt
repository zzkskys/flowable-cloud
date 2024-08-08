package org.example.flowablecloud

import org.flowable.engine.delegate.BpmnError
import org.flowable.engine.delegate.TaskListener
import org.flowable.task.service.delegate.DelegateTask
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/08
 * @author zzk
 */
@Component("payService")
class PayService : TaskListener {

    override fun notify(delegateTask: DelegateTask) {
        val price = delegateTask.getVariable("price", Long::class.java)
        if (price == null || price < 500) {
            throw BpmnError("50000", "余额不足")
        }
    }
}