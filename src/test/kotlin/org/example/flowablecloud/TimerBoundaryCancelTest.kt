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
 * 定时任务边界事件
 * @since : 2024/08/05
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class TimerBoundaryCancelTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Test
    fun 边界事件测试() {
        val instance = runtimeService.startProcessInstanceByKey("time-boundary")

        val t1 = taskService
            .createTaskQuery()
            .processInstanceId(instance.id)
            .active()
            .singleResult()
        assertEquals("apply", t1.taskDefinitionKey)

        taskService.complete(t1.id)

        val t2 = taskService
            .createTaskQuery()
            .processInstanceId(instance.id)
            .active()
            .singleResult()
        assertEquals("audit", t2.taskDefinitionKey)

        //暂停40秒，查看边界事件是否生效
        Thread.sleep(40_000)

        val t3 = taskService
            .createTaskQuery()
            .processInstanceId(instance.id)
            .active()
            .singleResult()
        assertEquals("audit_time_over", t3.taskDefinitionKey)

        taskService.complete(t3.id)
    }
}