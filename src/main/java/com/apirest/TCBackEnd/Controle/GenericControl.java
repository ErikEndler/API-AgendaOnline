package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class GenericControl<MODELO, DTO, REPOSITORIO extends CrudRepository<MODELO, Long>> {

	@Autowired
	REPOSITORIO repositorio;

	// Metodo Principal LISTAR UM -----------------------
	public Optional<MODELO> listar(Long id) {
		verificaList(id);
		Optional<MODELO> findById = repositorio.findById(id);
		return findById;
	}

	// Metodo Principal SALVAR -------------------------
	public MODELO salvar(DTO dto) {
		verificaSalvar(dto);
		MODELO modelo = transformaSalvar(dto);
		MODELO retorno = repositorio.save(modelo);
		posSalvar(retorno);
		return retorno;
	}

	// Metodo Principal ATUALIZAR (UPDATE) ---------------
	public MODELO editar(DTO dto) {
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
		repositorio.deleteById(id);
	}

	// --------------------------------------------------------------------
	protected abstract void verificaSalvar(DTO dto);

	protected abstract void verificUpdate(DTO dto);

	protected abstract void verificaListAll();

	protected abstract void verificaList(long id);

	protected abstract void verificaDeletar(long id);

	protected abstract MODELO transformaSalvar(DTO dto);

	protected abstract MODELO transformaEditar(DTO dto);

	protected abstract void posSalvar(MODELO obj);

	protected String MenssagemErro() {
		String msg = "Objeto";
		return msg;
	}

}
