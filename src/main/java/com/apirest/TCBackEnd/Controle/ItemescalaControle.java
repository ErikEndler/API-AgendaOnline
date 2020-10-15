package com.apirest.TCBackEnd.Controle;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.ItemEscalaDTO;
import com.apirest.TCBackEnd.Models.Escala;
import com.apirest.TCBackEnd.Models.ItemEscala;
import com.apirest.TCBackEnd.Repository.EscalaRepository;
import com.apirest.TCBackEnd.Repository.ItemEscalaRepository;
import com.apirest.TCBackEnd.Util.ResourceNotFoundException;

@Service
public class ItemescalaControle extends GenericControl<ItemEscala, ItemEscalaDTO, ItemEscalaRepository>{
	
	EscalaRepository escalaRepository;

	@Override
	protected void verificaSalvar(ItemEscalaDTO dto) {
		verificaEscala(dto);		
	}

	@Override
	protected void verificUpdate(ItemEscalaDTO dto) {
		verificaExiste(dto.getId());
		verificaEscala(dto);
	}

	@Override
	protected void verificaListAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void verificaList(long id) {
		verificaExiste(id);
	}

	@Override
	protected void verificaDeletar(long id) {
		verificaExiste(id);		
	}

	@Override
	protected ItemEscala transformaSalvar(ItemEscalaDTO dto) {
		return new ItemEscala(verificaEscala(dto), dto.getHrInicial(), dto.getHrFinal(), dto.getQtd());
	}

	@Override
	protected ItemEscala transformaEditar(ItemEscalaDTO dto) {
		return new ItemEscala(dto.getId(), verificaEscala(dto), dto.getHrInicial(), dto.getHrFinal(), dto.getQtd());
	}
	
	//----------------------------------------------------------------
	
	private void verificaExiste(long id) {
		Optional<ItemEscala> retorno = repositorio.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(MenssagemErro() + " nao encontrado para o ID: " + id));
	}
	
	//verifica e retorna a escala
	private Escala verificaEscala(ItemEscalaDTO dto) {
		if(dto.getEscala() == 0 ) {
			new ResourceNotFoundException("Campo Escala não informado corretamente !! \"NULO\"");

		}
		Optional<Escala> escala = escalaRepository.findById(dto.getEscala());
		return escala.orElseThrow(() -> new ResourceNotFoundException(
				MenssagemErro() + " nao encontrado para o ID: " + dto.getEscala()));
	}
	

}
