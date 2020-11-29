package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.EscalaDTO;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ServicoFuncionarioRepository;
import com.apirest.TCBackEnd.Util.DataHora;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class EscalaControle extends GenericControl<Escala, EscalaDTO, EscalaRepository> {

	@Autowired
	ServicoFuncionarioRepository servicoFuncionarioRepository;
	@Autowired
	DataHora dataHora;

	public Iterable<String> listaDayWeek() {
		return dataHora.listarDayWeek();
	}

	public Iterable<Escala> listarPorservico(long id) {
		verificaServicoFuncionario(id);
		return repositorio.findAllByServicoFuncionarioId(id);
	}

	@Override
	protected void verificaSalvar(EscalaDTO dto) {
		verificaServicoFuncionario(dto.getServicoFuncionario());
	}

	@Override
	protected void verificUpdate(EscalaDTO dto) {
		verificaESxiste(dto.getId());
		verificaServicoFuncionario(dto.getServicoFuncionario());
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
		return new Escala(verificaServicoFuncionario(dto.getServicoFuncionario()), dto.getDiaSemana());
	}

	@Override
	protected Escala transformaEditar(EscalaDTO dto) {
		return new Escala(dto.getId(), verificaServicoFuncionario(dto.getServicoFuncionario()), dto.getDiaSemana());
	}

	// ------------------------------------------
	private void verificaESxiste(long id) {
		Optional<Escala> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	public void cadastraEscalasServico(long servicoID) {
		Iterable<String> listaDias = dataHora.listarDayWeek();
		listaDias.forEach(n -> {
			salvar(new EscalaDTO(n, servicoID));
		});
	}

	// verifica e retorna o serviço
	private ServicoFuncionario verificaServicoFuncionario(long servicoFuncionario) {
		if (servicoFuncionario == 0) {
			throw new ResourceNotFoundException("Campo Serviço não informado corretamente !! \"NULO\"");
		}
		Optional<ServicoFuncionario> retorno = servicoFuncionarioRepository.findById(servicoFuncionario);
		return retorno.orElseThrow(() -> new ResourceNotFoundException(
				MenssagemErro() + " nao encontrado para o ID: " + servicoFuncionario));
	}

	@Override
	protected void posSalvar(Escala escala) {

	}

}
