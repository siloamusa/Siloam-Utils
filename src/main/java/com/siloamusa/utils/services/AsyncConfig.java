package com.siloamusa.utils.services;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync // Required to enable asynchronous processing
public class AsyncConfig {

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Number of threads to keep alive even if idle
        executor.setCorePoolSize(5); 
        
        // Maximum threads to spawn if queue is full
        executor.setMaxPoolSize(10); 
        
        // Maximum tasks to hold before spawning more threads
        executor.setQueueCapacity(100); 
        
        // Helpful for debugging logs
        executor.setThreadNamePrefix("EmailThread-"); 
        
        // Rejection policy if both queue and max threads are full
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        executor.initialize();
        return executor;
    }
}
