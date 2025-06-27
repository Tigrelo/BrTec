package Desafio_BrTec.service;

import Desafio_BrTec.entity.Tarefa;
import Desafio_BrTec.enums.StatusTarefa;
import Desafio_BrTec.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaProcessorService tarefaProcessorService;

    // --- MÉTODOS CRUD COM IMPLEMENTAÇÃO COMPLETA ---

    public Tarefa criarTarefa(Tarefa tarefa) {
        tarefa.setStatus(StatusTarefa.PENDENTE);
        return tarefaRepository.save(tarefa); // <-- O return que faltava
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll(); // <-- O return que faltava
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id); // <-- O return que faltava
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaDetails) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
        tarefa.setNome(tarefaDetails.getNome());
        tarefa.setStatus(tarefaDetails.getStatus());
        return tarefaRepository.save(tarefa); // <-- O return que faltava
    }

    public void deletarTarefa(Long id) {
        // Este método retorna 'void' (nada), então ele não precisa de 'return'.
        tarefaRepository.deleteById(id);
    }

    // --- MÉTODO DE PROCESSAMENTO SIMPLIFICADO ---
    @Async("taskExecutor")
    public void processarTarefa(Long tarefaId) {
        tarefaProcessorService.executarComRetry(tarefaId);
    }

    // --- MÉTODO DE RELATÓRIO ---
    public Map<StatusTarefa, Long> gerarRelatorio() {
        return tarefaRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Tarefa::getStatus, Collectors.counting()));
    }
}