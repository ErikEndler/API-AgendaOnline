package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.EscalaDTO;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class EscalaControle extends GenericControl<Escala, EscalaDTO, EscalaRepository> {

	@Autowired
	ServicoRepository servicoRepository;

	@Override
	protected void verificaSalvar(EscalaDTO dto) {
		verificaServico(dto);
	}

	@Override
	protected void verificUpdate(EscalaDTO dto) {
		verificaESxiste(dto.getId());
		verificaServico(dto);
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verificaList(long id) {
		verificaESxiste(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaESxiste(id);
	}

	@Override
	protected Escala transformaSalvar(EscalaDTO dto) {
		return new Escala(verificaServico(dto), dto.getDiaSemana());
	}

	@Override
	protected Escala transformaEditar(EscalaDTO dto) {
		return new Escala(dto.getId(), verificaServico(dto), dto.getDiaSemana());
	}

	// ------------------------------------------
	private void verificaESxiste(long id) {
		Optional<Escala> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	// verifica e retorna o serviço
	private Servico verificaServico(EscalaDTO dto) {
		if(dto.getServico() == 0) {
			new ResourceNotFoundException("Campo Serviço não informado corretamente !! \"NULO\"");
		}
		Optional<Servico> retorno = servicoRepository.findById(dto.getServico());
		return retorno.orElseThrow(() -> new ResourceNotFoundException(
				MenssagemErro() + " nao encontrado para o ID: " + dto.getServico()));
	}

}
