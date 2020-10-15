package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.ServicoDTO;
import com.apirest.TCBackEnd.DTO.ServicoFuncionarioDTO;
import com.apirest.TCBackEnd.Models.Categoria;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Repository.CategoriaRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class ServicoControle extends GenericControl<Servico, ServicoDTO, ServicoRepository> {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	protected void verificaSalvar(ServicoDTO dto) {
		verificaCategoria(dto);
	}

	@Override
	protected void verificUpdate(ServicoDTO dto) {
		verifiaExiste(dto.getId());
		verificaCategoria(dto);
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaList(long id) {
		verifiaExiste(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verifiaExiste(id);
	}

	@Override
	protected Servico transformaSalvar(ServicoDTO dto) {
		return new Servico(verificaCategoria(dto), dto.getNome(), dto.getDescricao());
	}

	@Override
	protected Servico transformaEditar(ServicoDTO dto) {
		return new Servico(dto.getId(), verificaCategoria(dto), dto.getNome(), dto.getDescricao());
	}

	protected String MenssagemErro() {
		String msg = "Serviço";
		return msg;
	}

	// ----------------------------- METODOS AUXILIARES ------------------
	private void verifiaExiste(long id) {
		Optional<Servico> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private Categoria verificaCategoria(ServicoDTO dto) {
		if (dto.getCategoria() == 0) {
			new ResourceNotFoundException("Campo categoria não informado corretamente !!");
		}
		Optional<Categoria> retorno = categoriaRepository.findById(dto.getCategoria());
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Categoria nao encontrado para o ID: " + dto.getCategoria()));
	}

	public ServicoFuncionario salvar2(@Valid ServicoFuncionarioDTO servicoFuncionarioDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
