package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.apirest.TCBackEnd.Models.AbstractModel;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

public abstract class GenericControl<MODELO, DTO, REPOSITORIO extends CrudRepository<MODELO, Long>> {

	@Autowired
	REPOSITORIO repositorio;

	// Metodo Principal LISTAR UM -----------------------
	public Optional<MODELO> listar(Long id) {
		verificaList(id);
		verifyIfObjectExists(id);
		Optional<MODELO> findById = repositorio.findById(id);
		return findById;
	}

	// Metodo Principal SALVAR -------------------------
	public MODELO salvar(DTO dto) {
		verificaSalvar(dto);
		MODELO modelo = transformaSalvar(dto);
		return repositorio.save(modelo);
	}

	// Metodo Principal ATUALIZAR (UPDATE) ---------------
	public MODELO editar(DTO dto) {
		verifyIfObjectExists(((AbstractModel) dto).getId());
		verificUpdate(dto);
		MODELO modelo = transformaEditar(dto);
		return repositorio.save(modelo);
	}

	// Metodo Principal LISTAR TODOS ---------------------
	public Iterable<MODELO> listarTodos() {
		verificaListAll();
		return repositorio.findAll();
	}

	// Metodo Principal DELETAR --------------------------
	public void deletarById(long id) {
		verificaDeletar(id);
		verifyIfObjectExists(id);
		repositorio.deleteById(id);
	}

	// --------------------------------------------------------------------
	protected abstract void verificaSalvar(DTO dto);

	protected abstract void verificUpdate(DTO dto);

	protected abstract void verificaListAll();

	protected abstract void verificaList(Long id);

	protected abstract void verificaDeletar(long id);

	protected abstract MODELO transformaSalvar(DTO dto);

	protected abstract MODELO transformaEditar(DTO dto);

	private MODELO verifyIfObjectExists(Long id) {
		String msg = MenssagemErro();
		Optional<MODELO> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(msg + " nao encontrado para o ID: " + id));
		return retorno.get();
	}

	protected String MenssagemErro() {
		String msg = "Objeto";
		return msg;
	}

}
