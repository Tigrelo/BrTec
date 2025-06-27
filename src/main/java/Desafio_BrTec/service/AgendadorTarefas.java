package Desafio_BrTec.service;

import Desafio_BrTec.entity.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AgendadorTarefas {

    @Autowired
    private TarefaService tarefaService;

    private final AtomicLong counter = new AtomicLong();

    @Scheduled(fixedRate = 5000)
    public void agendarNovaTarefa() { // <-- Correção: removido o "throws InterruptedException"
        System.out.println("Agendando nova tarefa...");

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setNome("Tarefa agendada #" + counter.incrementAndGet());
        Tarefa tarefaSalva = tarefaService.criarTarefa(novaTarefa);

        tarefaService.processarTarefa(tarefaSalva.getId());
    }
}