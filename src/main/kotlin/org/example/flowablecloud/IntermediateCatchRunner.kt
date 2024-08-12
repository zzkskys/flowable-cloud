//package org.example.flowablecloud
//
//import org.flowable.engine.RuntimeService
//import org.flowable.engine.TaskService
//import org.slf4j.LoggerFactory
//import org.springframework.boot.CommandLineRunner
//import org.springframework.stereotype.Component
//
///**
// * 中间捕获事件测试用例
// * @since : 2024/08/09
// * @author zzk
// */
//@Component
//class IntermediateCacheRunner(
//    private val runtimeService: RuntimeService,
//    private val taskService: TaskService
//) : CommandLineRunner {
//
//    companion object {
//        private val log = LoggerFactory.getLogger(IntermediateCacheRunner::class.java)
//    }
//
//    override fun run(vararg args: String?) {
//        log.info("启动流程实例......")
//
//        //intermediate_cache
//        val p1 = runtimeService.startProcessInstanceByKey("intermediate_cache")
//
//        val t1 = taskService
//            .createTaskQuery()
//            .active()
//            .taskDefinitionKey("apply")
//            .singleResult()
//
//        taskService.complete(t1.id)
//
//
//    }
//}