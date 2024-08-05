package org.example.flowablecloud

import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

/**
 * 信号事件流程
 * @since : 2024/08/05
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class SignalContractTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 信号事件测试流程() {
        val p1 = runtimeService.startProcessInstanceByKey("update_contract_process")
        val p2 = runtimeService.startProcessInstanceByKey("update_contract_process")


        val t1_1 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        val t2_1 = taskService
            .createTaskQuery()
            .processInstanceId(p2.processInstanceId)
            .active()
            .singleResult()


        assertEquals("apply", t1_1.taskDefinitionKey)
        assertEquals("apply", t2_1.taskDefinitionKey)

        taskService.complete(t1_1.id)
        taskService.complete(t2_1.id)

        val t1_2 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        val t2_2 = taskService
            .createTaskQuery()
            .processInstanceId(p2.processInstanceId)
            .active()
            .singleResult()

        assertEquals("audit", t1_2.taskDefinitionKey)
        assertEquals("audit", t2_2.taskDefinitionKey)

        runtimeService.signalEventReceived("修改合同信号")


        val t1_3 = taskService
            .createTaskQuery()
            .processInstanceId(p1.processInstanceId)
            .active()
            .singleResult()

        val t2_3 = taskService
            .createTaskQuery()
            .processInstanceId(p2.processInstanceId)
            .active()
            .singleResult()


        assertEquals("update_contract", t1_3.taskDefinitionKey)
        assertEquals("update_contract", t2_3.taskDefinitionKey)
    }


}