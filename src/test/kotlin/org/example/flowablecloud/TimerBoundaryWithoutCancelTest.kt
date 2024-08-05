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
import kotlin.test.assertTrue

/**
 * 定时任务非中断边界事件测试
 * @since : 2024/08/05
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class TimerBoundaryWithoutCancelTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService


    @Test
    fun 定时任务非中断边界事件测试() {
        val instance = runtimeService.startProcessInstanceByKey("time-boundary-without-cancel")

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


        //暂停40秒
        Thread.sleep(40_000)

        val tasks = taskService
            .createTaskQuery()
            .processInstanceId(instance.id)
            .list()


        assertEquals(2, tasks.size)

        assertTrue { tasks.any { it.taskDefinitionKey == "audit" } }
        assertTrue { tasks.any { it.taskDefinitionKey == "audit_time_over" } }

        taskService.complete(tasks[0].id)
        taskService.complete(tasks[1].id)

        val tasks2 = taskService
            .createTaskQuery()
            .processInstanceId(instance.id)
            .list()

        assertTrue { tasks2.isEmpty() }

    }
}