package com.apirest.TCBackEnd.Util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class DataHora {

	private DateTimeFormatter formataHora1 = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	private DateTimeFormatter formataHora = DateTimeFormatter.ofPattern("HH:mm");


	private DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private DateTimeFormatter formataDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

	// transforma uma string em LocalDateTime
	public LocalDateTime stringemDateTime(String string) {
		try {
			return LocalDateTime.from(formataDataHora.parse(string));

		} catch (Exception e) {
			//System.out.println("ENTRANDO CATCH----");
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
			//System.out.println("string : "+string);
			return LocalTime.from(formataHora.parse(string));
		} catch (Exception e) {
			//System.out.println("ENTRANDO CATCH----");
			throw new ResourceNotFoundException(" Erro converção Hora, formato invalido. Detalhe : " + string);
		}
	}

	// transforma um LocalTime em string
	public String horaEmString(LocalTime localtime) {
		return formataHora.format(localtime);
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
			throw new ResourceNotFoundException(" Erro converção Data:" + e.getMessage());
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
		    //System.out.println(c.getDisplayName(TextStyle.FULL, new Locale("pt")));
		    lista.add(c.getDisplayName(TextStyle.FULL, new Locale("pt")));
		}
		return lista;
		
	}

	// ---------------METDOD TESTE---------------
//	@EventListener(ContextRefreshedEvent.class)
	private void teste() {
		//DayOfWeek dayOfWeek[] = null  ;
		System.out.println("INICIANDO TESTE DE HORA DATA");

		// teste String em hora 11-30-33
		System.out.println("String Hora '11:30:33' : " + stringEmHora("11:30:33"));

		//teste string hora simulada em localtime
		System.out.println("String de hora '14:32:00' em Local time"+stringEmHora("14:32:00"));
		
		// testa HR.now() em string
		System.out.println("LocalTime.now() : " + LocalTime.now());

		System.out.println("Hora de agora .now() em string : " + horaEmString(LocalTime.now()));

		//string em hora 8:00:00
		System.out.println("String de hora '08:00:00' em Local time "+stringEmHora("08:00:00"));
		System.out.println("String de hora '08:30:00' em Local time "+stringEmHora("08:30:00"));

		System.out.println("String de hora '08:00:00' em MINUTOS "+stringEmHora("08:00:00").getHour()*60);

		Duration duracao = Duration.ofDays(1);
		System.out.println("Duration.ofDays(1) "+duracao.toMinutes());

		Duration between = Duration.between(stringEmHora("08:00:00"), stringEmHora("10:30:00"));
		System.out.println("DURATION "+between.toHoursPart());
		System.out.println("DURATION "+between.toMinutesPart());

		System.out.println("-------now() em localtime ---------"+LocalDateTime.now().toLocalTime());



	}

}
