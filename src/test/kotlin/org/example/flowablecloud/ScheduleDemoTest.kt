package org.example.flowablecloud

import org.flowable.engine.HistoryService
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

/**
 *
 * @since : 2024/08/02
 * @author zzk
 */
@ExtendWith(FlowableSpringExtension::class)
@SpringBootTest
@AutoConfigureTestDatabase
class ScheduleDemoTest {


    @Autowired
    lateinit var historyService: HistoryService

    @Test
//    @Deployment(resources = ["processes/schedule-start.bpmn20.xml"])
    fun 定时开始事件测试() {
        Thread.sleep(61_000)

        val instances = historyService
            .createHistoricProcessInstanceQuery()
            .list()

        assertEquals(1, instances.size)

    }
}