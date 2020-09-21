package com.apirest.TCBackEnd.DTO;

import java.util.ArrayList;
import java.util.List;

import com.apirest.TCBackEnd.Models.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
	
	private long id;
	private String nome;
	private String descricao;
	
	public static CategoriaDTO categoriaResposta(Categoria categoria) {
		return new CategoriaDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
	}

	// Recebe uma lista de categorias e transforma a lista para o formato de resposta
	public static Iterable<CategoriaDTO> listarResposta(Iterable<Categoria> listaUsuarios) {
		// Cria a lista que sera retornada
		List<CategoriaDTO> listaDTO = new ArrayList<CategoriaDTO>();
		// Faz um for na lista recebida no metodo
		for (Categoria categoria : listaUsuarios) {
			listaDTO.add(categoriaResposta(categoria));
		}
		return listaDTO;
	}

}
