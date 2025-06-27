package Desafio_BrTec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor") // Damos um nome ao nosso Executor
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Define o número máximo de threads que podem estar ativas ao mesmo tempo
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        // Define o tamanho da fila de tarefas esperando para serem executadas
        executor.setQueueCapacity(100);
        // Define um prefixo para o nome das threads para facilitar a leitura dos logs
        executor.setThreadNamePrefix("TaskThread-");
        executor.initialize();
        return executor;
    }
}