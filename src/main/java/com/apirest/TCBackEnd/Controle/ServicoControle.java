package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apirest.TCBackEnd.DTO.ServicoDTO;
import com.apirest.TCBackEnd.Models.Categoria;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Repository.CategoriaRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

public class ServicoControle extends GenericControl<Servico, ServicoDTO, ServicoRepository> {

	@Autowired
	ServicoRepository servicoRepository;
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	protected void verificaSalvar(ServicoDTO dto) {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Servico transformaEditar(ServicoDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	protected String MenssagemErro() {
		String msg = "Serviço";
		return msg;
	}

	// -----------------------------
	private void verifiaExiste(long id) {
		Optional<Servico> retorno = servicoRepository.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	private void verificaCategoria(ServicoDTO dto) {
		Optional<Categoria> retorno = categoriaRepository.findById(dto.getCategoria());
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + dto.getCategoria()));
	}

}
