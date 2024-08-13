package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

/**
 * 信号抛出事件测试
 * @since : 2024/08/13
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class PayApplySignalThrowEvent {


    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 信号抛出捕获事件测试() {
        val p1 = runtimeService.startProcessInstanceByKey("signal_pay")

        val 付款申请 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pay_apply")
            .singleResult()

        taskService.complete(付款申请.id)

        val 业务主管审批 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("executive_audit")
            .singleResult()

        taskService.complete(业务主管审批.id)

       val 财务主管审批 = taskService
           .createTaskQuery()
           .active()
           .taskDefinitionKey("treasurer_audit")
           .singleResult()

        val 部门主管审批 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("dept_manager_audit")
            .singleResult()


        assertNotNull(财务主管审批)
        assertNotNull(部门主管审批)


        taskService.complete(财务主管审批.id)
        taskService.complete(部门主管审批.id)

        val 财务打款 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("pay")
            .singleResult()
        taskService.complete(财务打款.id)
    }
}