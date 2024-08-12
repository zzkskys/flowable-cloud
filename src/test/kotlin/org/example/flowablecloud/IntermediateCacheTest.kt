package org.example.flowablecloud

import org.flowable.engine.HistoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

/**
 * todo : h2 单元测试针对定时器的相关测试总是失败，需要重新考虑测试效果
 * @since : 2024/08/09
 * @author zzk
 */

@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class IntermediateCacheTest {

    @Autowired
    lateinit var runtimeService: RuntimeService

    @Autowired
    lateinit var taskService: TaskService

    @Autowired
    lateinit var historyService: HistoryService


    @Test
    fun 中间捕获事件测试() {
        //intermediate_cache
        val p1 = runtimeService.startProcessInstanceByKey("intermediate_cache")

        val 出库申请 = taskService
            .createTaskQuery()
            .active()
            .taskDefinitionKey("apply")
            .singleResult()

        taskService.complete(出库申请.id)
        Thread.sleep(61_000)

        val activity = historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(p1.id)
            .unfinished()
            .list()


        activity.forEach {
            println("未完成的活动的id: ${it.id}, name: ${it.activityName},processInstanceId: ${it.processInstanceId}")
        }

        val activit2 = historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(p1.id)
            .unfinished()
            .list()


        activit2.forEach {
            println("未完成的活动的id: ${it.id}, name: ${it.activityName},processInstanceId: ${it.processInstanceId}")
        }


    }
}