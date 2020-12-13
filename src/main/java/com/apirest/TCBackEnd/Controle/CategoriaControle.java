package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.CategoriaDTO;
import com.apirest.TCBackEnd.Models.Categoria;
import com.apirest.TCBackEnd.Repository.CategoriaRepository;
import com.apirest.TCBackEnd.Util.Error.ResourceNotFoundException;

@Service
public class CategoriaControle extends GenericControl<Categoria, CategoriaDTO, CategoriaRepository> {

	@Override
	protected void verificaSalvar(CategoriaDTO dto) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Categoria verificUpdate(CategoriaDTO dto) {
		Categoria retorno = verificaExixte(dto.getId()).get();
		return retorno;
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void verificaList(long id) {
		verificaExixte(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaExixte(id);
	}

	@Override
	protected Categoria transformaSalvar(CategoriaDTO dto) {
		return new Categoria(dto.getNome(), dto.getDescricao());
	}

	@Override
	protected Categoria transformaEditar(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome(), dto.getDescricao());
	}

	// -------------------------METODOS AUXILIARES ----------------------------
	@EventListener(ContextRefreshedEvent.class)
	private void adicionaDefault() {
		System.out.println("---função inicial, verifica se há categorias no sistema");
		if (repositorio.count() == 0) {
			Categoria categoria = new Categoria();
			categoria.setNome("default");
			categoria.setDescricao("categoria geral");
			repositorio.save(categoria);
		}
	}

	private Optional<Categoria> verificaExixte(long id) {
		Optional<Categoria> categoria = repositorio.findById(id);
		categoria
				.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
		return categoria;
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Categoria";
		return msg;
	}

	@Override
	protected void posSalvar(Categoria categoria) {
		// TODO Auto-generated method stub

	}

}
