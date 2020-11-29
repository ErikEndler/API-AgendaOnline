package com.apirest.TCBackEnd.Controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.Models.Disponibilidade;
import com.apirest.TCBackEnd.Repository.DisponibilidadeRepository;

@Service
public class DisponibilidadeControle {

	@Autowired
	DisponibilidadeRepository repositorio;

	@EventListener(ContextRefreshedEvent.class)
	private void disponibilidadeDefault() {
		System.out.println("---função inicial, verifica cadastro de disponibilidade");
		verificaExistencia();
	}

	private void verificaExistencia() {
		if (repositorio.count() == 0) {
			cadastraDefault();
		}
	}

	private void cadastraDefault() {
		Disponibilidade disp = new Disponibilidade();
		disp.setNome("geral");
		disp.setQtd(1);
		repositorio.save(disp);
	}

	public Disponibilidade updateGeral(Disponibilidade disponibilidade) {
		Disponibilidade disp = repositorio.findByNome(disponibilidade.getNome()).get();
		disp.setQtd(disponibilidade.getQtd());
		return repositorio.save(disp);
	}

	public Disponibilidade listGeral() {
		return repositorio.findByNome("geral").get();
	}

}
