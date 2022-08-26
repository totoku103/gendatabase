package me.totoku103.gendatabase;

import lombok.extern.slf4j.Slf4j;
import me.totoku103.gendatabase.component.InsertEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;

import java.util.concurrent.*;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class GendatabaseApplication {

    private static final int THREAD_COUNT = 1;
    private static final int SEND_MESSAGE_COUNT = 1;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ConfigurableApplicationContext app = SpringApplication.run(GendatabaseApplication.class, args);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREAD_COUNT; i++) {
            InsertEngine bean = app.getBean(InsertEngine.class);
            Future<String> submit = executorCompletionService.submit(() -> bean.process(SEND_MESSAGE_COUNT));
        }
        executorService.shutdown();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Future<String> take = executorCompletionService.take();
            log.info("result: {}", take.get());
        }

    }

}
