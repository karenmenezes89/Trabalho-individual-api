package org.serratec.avaliacao.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.serratec.avaliacao.Funcionario;
import org.serratec.avaliacao.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

  
    @GetMapping
    public List<Funcionario> listar() {
        return repository.findAll();
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Funcionario> criar( @RequestBody @Valid Funcionario funcionario) {
        Funcionario salvo = repository.save(funcionario);
        return ResponseEntity.status(201).body(salvo); 
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable Long id, @Valid @RequestBody Funcionario dados) {
        return repository.findById(id).map(funcionario -> {
            funcionario.setNome(dados.getNome());
            funcionario.setEndereco(dados.getEndereco());
            return ResponseEntity.ok(repository.save(funcionario)); 
        }).orElse(ResponseEntity.notFound().build()); 
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id).map(funcionario -> {
            repository.delete(funcionario);
            return ResponseEntity.noContent().build(); 
        }).orElse(ResponseEntity.notFound().build());
    }
}