package Desafio_BrTec.service;

import Desafio_BrTec.entity.Tarefa;
import Desafio_BrTec.enums.StatusTarefa;
import Desafio_BrTec.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class TarefaProcessorService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Retryable(value = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void executarComRetry(Long tarefaId) {
        System.out.println("[PROCESSOR] Processando tarefa ID: " + tarefaId);
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (ThreadLocalRandom.current().nextInt(10) < 4) {
            System.err.println("[PROCESSOR] FALHA SIMULADA na tarefa: " + tarefa.getId());
            throw new RuntimeException("Erro de processamento simulado!");
        }

        tarefa.setStatus(StatusTarefa.EM_PROCESSAMENTO);
        tarefaRepository.save(tarefa);

        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextLong(1, 6));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        tarefa.setStatus(StatusTarefa.CONCLUIDA);
        tarefaRepository.save(tarefa);
        System.out.println("[PROCESSOR] Tarefa ID: " + tarefa.getId() + " concluída.");
    }

    @Recover
    public void recover(RuntimeException e, Long tarefaId) {
        System.err.println("[RECOVER] Todas as tentativas falharam para a tarefa " + tarefaId + ". Marcando como FALHOU.");
        tarefaRepository.findById(tarefaId).ifPresent(tarefa -> {
            tarefa.setStatus(StatusTarefa.FALHOU);
            tarefaRepository.save(tarefa);
        });
    }
}