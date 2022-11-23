package br.com.egois.schemaregistry.controller;

import br.com.egois.schemaregistry.dto.TransacaoDTO;

import br.com.egois.schemaregistry.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @Value("${app.topic}")
    private String topic;

    @GetMapping
    public String status() {
        return "Topico " + topic;
    }

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody TransacaoDTO payload) {

       service.send(payload);

        return ResponseEntity.ok("ok");
    }
}
