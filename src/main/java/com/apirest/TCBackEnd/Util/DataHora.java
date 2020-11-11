package com.apirest.TCBackEnd.Util;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.apirest.TCBackEnd.DTO.AgendamentoDTO;

@Service
public class DataHora {

	private DateTimeFormatter formataHora1 = DateTimeFormatter.ofPattern("HH-mm-ss");

	private DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private DateTimeFormatter formataDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss", Locale.FRANCE);

	// transforma uma string em LocalDateTime
	public LocalDateTime stringemDateTime(String string) {
		try {
			return LocalDateTime.from(formataDataHora.parse(string));

		} catch (Exception e) {
			System.out.println("ENTRANDO CATCH----");
			throw new ResourceNotFoundException(" Erro converção, DateTime formato invalido. Detalhe : " + e);
		}

	}

	// transforma um LocalDateTime em string
	public String dateTimeEmString(LocalDateTime localDateTime) {
		return formataDataHora.format(localDateTime);
	}

	// tranbsforma uma string em LocalTime
	public LocalTime stringEmHora(String string) {
		try {
			return LocalTime.from(formataHora1.parse(string));
		} catch (Exception e) {
			System.out.println("ENTRANDO CATCH----");
			throw new ResourceNotFoundException(" Erro converção Hora, formato invalido. Detalhe : " + e.getMessage());
		}
	}

	// transforma um LocalTime em string
	public String horaEmString(LocalTime localtime) {
		return formataHora1.format(localtime);
	}

	// tranbsforma uma string em LocalDate
	public LocalDate stringEmData(String string) {
		try {
			return LocalDate.from(formataData.parse(string));
		} catch (Exception e) {
			throw new ResourceNotFoundException(" Erro converção Data, formato invalido. Detalhe : " + e.getMessage());
		}
	}

	// transforma um LocalDate em string
	public String dataEmString(LocalDate localDate) {
		try {
			return formataData.format(localDate);

		} catch (Exception e) {
			throw new ResourceNotFoundException(" Erro converção Data:" + e);
		}
	}

	// Retorna dia da semana
	public String pegaDiaSemana(LocalDate data) {
		String dia = data.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt"));
		return dia;
	}
	
	public Iterable<String> listarDayWeek() {
		List<String> lista = new ArrayList<String>();
		for (DayOfWeek c : DayOfWeek.values()) {
		    System.out.println(c.getDisplayName(TextStyle.FULL, new Locale("pt")));
		    lista.add(c.getDisplayName(TextStyle.FULL, new Locale("pt")));
		}
		return lista;
		
	}

	// ---------------METDOD TESTE---------------
	@EventListener(ContextRefreshedEvent.class)
	private void teste() {
		//DayOfWeek dayOfWeek[] = null  ;
		System.out.println("INICIANDO TESTE DE HORA DATA");

		// teste String em hora 11-30-33
		System.out.println("String Hora '11-30-33' : " + stringEmHora("11-30-33"));

		// testa HR.now() em string
		System.out.println("LocalTime.now() : " + LocalTime.now());

		System.out.println("Hora de agora .now() em string : " + horaEmString(LocalTime.now()));

		System.out.println("TESTE DE DATA");
		// teste String em data 2020-02-22
		System.out.println("String Data '2020-02-22' : " + stringEmData("2020-02-22"));
		System.out.println("String Data '2020-10-20' dia semana : " + stringEmData("2020-10-20").getDayOfWeek());

		// testa data.now() em strin
		System.out.println("LocalDate.now() : " + LocalDate.now());
		System.out.println("LocalDate.now() dia da SEMANA : "
				+ LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt")));
		

		System.out.println("Data .now() em string : " + dataEmString(LocalDate.now()));

	}

}
