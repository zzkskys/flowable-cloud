package org.example.flowablecloud

import org.flowable.engine.delegate.BpmnError
import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @since : 2024/08/09
 * @author zzk
 */
@Component("lockInventoryService")
class LockInventoryService : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(LockInventoryService::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        log.info("锁定库存......")
    }
}

@Component("deductInventoryService")
class DeductInventoryService : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(DeductInventoryService::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        val count = execution.getVariable("count", Long::class.java)
        if (count == null || count <= 0) {
            throw BpmnError("20000", "库存不足")
        }
        log.info("减少库存......")
    }
}

@Component("releaseInventoryService")
class ReleaseInventoryService : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(ReleaseInventoryService::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        log.info("释放锁定的库存.......")
    }
}


@Component("costReturnService")
class CostReturnService : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(CostReturnService::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        log.info("支付的费用退回......")
    }

}

@Component("autoCancelOrderService")
class AutoCancelOrderService : JavaDelegate {

    companion object {
        private val log = LoggerFactory.getLogger(AutoCancelOrderService::class.java)
    }


    override fun execute(execution: DelegateExecution) {
        log.info("自动取消订单......")
    }

}