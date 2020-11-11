package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.EscalaDTO;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class EscalaControle extends GenericControl<Escala, EscalaDTO, EscalaRepository> {

	@Autowired
	ServicoRepository servicoRepository;
	@Autowired
	DataHora dataHora;

	public Iterable<String> listaDayWeek() {
		return dataHora.listarDayWeek();
	}

	public Iterable<Escala> listarPorservico(long id) {
		verificaServico(id);
		return repositorio.findAllByServico(id);
	}

	@Override
	protected void verificaSalvar(EscalaDTO dto) {
		verificaServico(dto.getServico());
	}

	@Override
	protected void verificUpdate(EscalaDTO dto) {
		verificaESxiste(dto.getId());
		verificaServico(dto.getServico());
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
		return new Escala(verificaServico(dto.getServico()), dto.getDiaSemana());
	}

	@Override
	protected Escala transformaEditar(EscalaDTO dto) {
		return new Escala(dto.getId(), verificaServico(dto.getServico()), dto.getDiaSemana());
	}

	// ------------------------------------------
	private void verificaESxiste(long id) {
		Optional<Escala> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	// verifica e retorna o serviço
	private Servico verificaServico(long servico) {
		if (servico == 0) {
			new ResourceNotFoundException("Campo Serviço não informado corretamente !! \"NULO\"");
		}
		Optional<Servico> retorno = servicoRepository.findById(servico);
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + servico));
	}

}
