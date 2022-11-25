package br.com.egois.schemaregistry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO{

    private String key;

    private String nome;

    private String documento;

    private Boolean situacao;

    private Integer idade;

    private String apelido;


}
