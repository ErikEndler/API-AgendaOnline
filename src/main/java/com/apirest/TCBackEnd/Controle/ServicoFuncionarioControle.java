package com.apirest.TCBackEnd.Controle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.ServicoFuncionarioDTO;
import com.apirest.TCBackEnd.Models.Servico;
import com.apirest.TCBackEnd.Models.ServicoFuncionario;
import com.apirest.TCBackEnd.Models.Usuario;
import com.apirest.TCBackEnd.Repository.ServicoFuncionarioRepository;
import com.apirest.TCBackEnd.Repository.ServicoRepository;
import com.apirest.TCBackEnd.Repository.UsuarioRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class ServicoFuncionarioControle
		extends GenericControl<ServicoFuncionario, ServicoFuncionarioDTO, ServicoFuncionarioRepository> {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ServicoRepository servicoRepository;
	@Autowired
	EscalaControle escalaControle;

	public Optional<ServicoFuncionario> listarPorServico(long idServico) {
		Optional<ServicoFuncionario> servicoFuncionario = repositorio.findByServicoId(idServico);
		servicoFuncionario
				.orElseThrow(() -> new ResourceNotFoundException("erro ao buscar Servico-Funcionario pelo serviço"));
		return servicoFuncionario;
	}

	public List<Servico> listarServicosDoFuncionario(long idFuncionario) {
		List<Servico> listServico = repositorio.findByFuncionarioId(idFuncionario).stream()
				.map(ServicoFuncionario::getServico).collect(Collectors.toList());
		return listServico;
	}

	public void deletar(long funcionarioId, long servicoId) {
		verificaFuncionario(funcionarioId);
		verificaServico(servicoId);
		long id = repositorio.findByFuncionarioIdAndServicoId(funcionarioId, servicoId).get().getId();
		repositorio.deleteById(id);
	}

	@Override
	protected void verificaSalvar(ServicoFuncionarioDTO dto) {
		verificaFuncionario(dto.getFuncionarioId());
		verificaServico(dto.getServicoId());
		verificaAtribuicao(dto);
	}

	@Override
	protected ServicoFuncionario verificUpdate(ServicoFuncionarioDTO dto) {
		ServicoFuncionario retorno = verifiaExiste(dto.getId()).get();
		verificaFuncionario(dto.getFuncionarioId());
		verificaServico(dto.getServicoId());
		return retorno;
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
	protected ServicoFuncionario transformaSalvar(ServicoFuncionarioDTO dto) {
		return new ServicoFuncionario(verificaFuncionario(dto.getFuncionarioId()), verificaServico(dto.getServicoId()));
	}

	@Override
	protected ServicoFuncionario transformaEditar(ServicoFuncionarioDTO dto) {
		return new ServicoFuncionario(dto.getId(), verificaFuncionario(dto.getFuncionarioId()),
				verificaServico(dto.getServicoId()));
	}

	// -------------------------//---------------------
	// verifica se funcionario ja possui aquele serviço atribuido ha ele
	private void verificaAtribuicao(ServicoFuncionarioDTO dto) {
		if (repositorio.findByFuncionarioIdAndServicoId(dto.getFuncionarioId(), dto.getServicoId()).isPresent()) {
			throw new ResourceNotFoundException("Funcionario ja possui essa atribuição");
		}
		// return true;
	}

	private Optional<ServicoFuncionario> verifiaExiste(long id) {
		Optional<ServicoFuncionario> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
		return retorno;
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Serviço-Funcionario";
		return msg;
	}

	private Servico verificaServico(long servicoId) {
		if (servicoId == 0) {
			throw new ResourceNotFoundException("Campo Serviço não informado corretamente !!");
		}
		Optional<Servico> retorno = servicoRepository.findById(servicoId);
		return retorno
				.orElseThrow(() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + servicoId));
	}

	private Usuario verificaFuncionario(long idFuncionario) {
		if (idFuncionario == 0) {
			throw new ResourceNotFoundException("Campo Funcionario não informado corretamente !!");
		}
		Optional<Usuario> retorno = usuarioRepository.findById(idFuncionario);
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Funcionario nao encontrado para o ID: " + idFuncionario));
	}

	@Override
	protected void posSalvar(ServicoFuncionario servicoFuncionario) {
		escalaControle.cadastraEscalasServicoFuncionario(servicoFuncionario.getId());
	}

}
