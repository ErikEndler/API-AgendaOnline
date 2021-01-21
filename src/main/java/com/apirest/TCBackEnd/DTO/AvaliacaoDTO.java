package com.apirest.TCBackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
	private long id;
	private AtendimentoDTO atendimento;
	private int notaCliente;
	private int notaFuncionario;
	private String obsCliente;
	private String obsuncionario;

}
