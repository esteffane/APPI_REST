package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TarefaController {
    
    private List<Tarefa> tarefas = new ArrayList<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @GetMapping(value = "")
    public List<Tarefa> listarTarefas() {
        return tarefas;
    }

    @PostMapping
    public Tarefa adicionarTarefa(@RequestBody Tarefa tarefa) {
        tarefa.setId(nextId.getAndIncrement());
        tarefas.add(tarefa);
        return tarefa;
    }

    @GetMapping("/{id}")
    public Tarefa obterTarefaPorId(@PathVariable int id) {
        return tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable int id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (tarefaExistente != null) {
            tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
            tarefaExistente.setFeita(tarefaAtualizada.isFeita());
        }

        return tarefaExistente;
    }

    @DeleteMapping("/{id}")
    public void excluirTarefa(@PathVariable int id) {
        tarefas.removeIf(t -> t.getId() == id);
    }
}