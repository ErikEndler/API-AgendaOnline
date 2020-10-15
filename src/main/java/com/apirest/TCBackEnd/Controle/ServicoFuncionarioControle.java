package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.ServicoFuncionarioDTO;
import com.apirest.TCBackEnd.Models.Categoria;
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

	@Override
	protected void verificaSalvar(ServicoFuncionarioDTO dto) {
		verificaFuncionario(dto.getFuncionarioId());
		verificaServico(dto.getServicoId());
	}

	@Override
	protected void verificUpdate(ServicoFuncionarioDTO dto) {
		verifiaExiste(dto.getId());
		verificaFuncionario(dto.getFuncionarioId());
		verificaServico(dto.getServicoId());
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
		return new ServicoFuncionario(verificaFuncionario(dto), verificaServico(dto));
	}

	@Override
	protected ServicoFuncionario transformaEditar(ServicoFuncionarioDTO dto) {
		return new ServicoFuncionario(dto.getId(), verificaFuncionario(dto), verificaServico(dto));
	}

	// -------------------------
	private void verifiaExiste(long id) {
		Optional<ServicoFuncionario> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}

	@Override
	protected String MenssagemErro() {
		String msg = "Serviço-Funcionario";
		return msg;
	}

	private void verificaFuncionario(long funcionarioId) {
		Optional<Usuario> funcionario = usuarioRepository.findById(funcionarioId);
		funcionario.orElseThrow(
				() -> new ResourceNotFoundException("Funcionario nao encontrado para o ID: " + funcionarioId));
	}

	private void verificaServico(long id) {
		Optional<Servico> funcionario = servicoRepository.findById(id);
		funcionario.orElseThrow(() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + id));
	}

	private Servico verificaServico(ServicoFuncionarioDTO dto) {
		if (dto.getServicoId() == 0) {
			new ResourceNotFoundException("Campo Serviço não informado corretamente !!");
		}
		Optional<Servico> retorno = servicoRepository.findById(dto.getServicoId());
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Serviço nao encontrado para o ID: " + dto.getServicoId()));
	}

	private Usuario verificaFuncionario(ServicoFuncionarioDTO dto) {
		if (dto.getFuncionarioId() == 0) {
			new ResourceNotFoundException("Campo Funcionario não informado corretamente !!");
		}
		Optional<Usuario> retorno = usuarioRepository.findById(dto.getServicoId());
		return retorno.orElseThrow(
				() -> new ResourceNotFoundException("Funcionario nao encontrado para o ID: " + dto.getServicoId()));
	}

}
