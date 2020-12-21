package com.apirest.TCBackEnd.Controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.EscalaDTO;
import com.apirest.TCBackEnd.DTO.ServicoFuncionarioDTO;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoFuncionarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.Error.ResourceNotFoundException;

@Service
public class EscalaControle extends GenericControl<Escala, EscalaDTO, EscalaRepository> {

	@Autowired
	ServicoFuncionarioRepository servicoFuncionarioRepository;
	@Autowired
	DataHora dataHora;

	public List<List<Escala>> escalasFuncionarioServico(long funcionarioId, List<Long> servicosId) {
		List<List<Escala>> listas = new ArrayList<>();

		for (long servico : servicosId) {
			List<Escala> listaEscalas = new ArrayList<>();
			listas.add(verificaERetorna(funcionarioId, servico, listaEscalas));
		}
		return listas;
	}

	private List<Escala> verificaERetorna(long funcionarioId, long servico, List<Escala> listaEscalas) {
		List<Escala> escalas = repositorio
				.findByServicoFuncionarioFuncionarioIdAndServicoFuncionarioServicoId(funcionarioId, servico);
		escalas.forEach(e -> listaEscalas.add(e));

		return listaEscalas;
	}

	public Iterable<String> listaDayWeek() {
		return dataHora.listarDayWeek();
	}

	public Iterable<Escala> listarPorservicoFuncionario(long id) {
		verificaServicoFuncionario(id);
		return repositorio.findAllByServicoFuncionarioId(id);
	}

	@Override
	protected void verificaSalvar(EscalaDTO dto) {
		verificaServicoFuncionario(dto.getServicoFuncionario().getId());
	}

	@Override
	protected Escala verificUpdate(EscalaDTO dto) {
		Escala retorno = verificaESxiste(dto.getId()).get();
		verificaServicoFuncionario(dto.getServicoFuncionario().getServico().getId());
		return retorno;
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
		return new Escala(verificaServicoFuncionario(dto.getServicoFuncionario().getId()), dto.getDiaSemana());
	}

	@Override
	protected Escala transformaEditar(EscalaDTO dto) {
		return new Escala(dto.getId(), verificaServicoFuncionario(dto.getServicoFuncionario().getId()),
				dto.getDiaSemana());
	}

	// ------------------------------------------
	private Optional<Escala> verificaESxiste(long id) {
		Optional<Escala> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException("escala nao encontrado para o ID: " + id));
		return retorno;
	}

	public void cadastraEscalasServicoFuncionario(ServicoFuncionario servicoFuncionario) {
		Iterable<String> listaDias = dataHora.listarDayWeek();
		listaDias.forEach(day -> {
			salvar(new EscalaDTO(day, ServicoFuncionarioDTO.ServicoFuncionarioResposta(servicoFuncionario)));
		});
	}

	// verifica e retorna o serviço
	private ServicoFuncionario verificaServicoFuncionario(long servicoFuncionario) {
		if (servicoFuncionario == 0) {
			throw new ResourceNotFoundException("Campo Serviço não informado corretamente !! \"NULO\"");
		}
		Optional<ServicoFuncionario> retorno = servicoFuncionarioRepository.findById(servicoFuncionario);
		return retorno.orElseThrow(() -> new ResourceNotFoundException(
				"Servico-Funcionario nao encontrado para o ID: " + servicoFuncionario));
	}

	@Override
	protected void posSalvar(Escala escala) {

	}

}
