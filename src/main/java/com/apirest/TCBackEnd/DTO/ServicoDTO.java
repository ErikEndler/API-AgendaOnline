package com.apirest.TCBackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicoDTO {
	
	private long id;
	private long categoria;
	private String nome;
	private String descricao;

}
