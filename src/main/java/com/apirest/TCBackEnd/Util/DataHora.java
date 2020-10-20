package com.apirest.TCBackEnd.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DataHora {

	// private DateTimeFormatter formataHora =
	// DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL);
	//Day of week and month in French
    LocalDate localDate=LocalDate.of(2016,01,01);

    String dateInFrench=localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",Locale.FRENCH));
    
 
    //Day of week and month in Spanish
    Locale spanishLocale=new Locale("es", "ES");
    String dateInSpanish=localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy",spanishLocale));


	private DateTimeFormatter formataHora1 = DateTimeFormatter.ofPattern("HH-mm-ss",Locale.FRANCE);

	private DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.FRANCE);

	private DateTimeFormatter formataDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss",Locale.FRANCE);

	// tranbsforma uma string em LocalDateTime
	public LocalDateTime stringemDateTime(String string) {
		return LocalDateTime.from(formataDataHora.parse(string));
	}

	// transforma um LocalDateTime em string
	public String dateTimeEmString(LocalDateTime localDateTime) {
		return formataDataHora.format(localDateTime);
	}

	// tranbsforma uma string em LocalTime
	public LocalTime stringEmHora(String string) {
		return LocalTime.from(formataHora1.parse(string));
	}

	// transforma um LocalTime em string
	public String horaEmString(LocalTime localtime) {
		return formataHora1.format(localtime);
	}

	// tranbsforma uma string em LocalDate
	public LocalDate stringEmData(String string) {
		return LocalDate.from(formataData.parse(string));
	}

	// transforma um LocalDate em string
	public String dataEmString(LocalDate localDate) {
		return formataData.format(localDate);
	}

	@EventListener(ContextRefreshedEvent.class)
	private void teste() {
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

		// testa data.now() em string
		System.out.println("LocalDate.now() : " + LocalDate.now());
		System.out.println("LocalDate.now() dia da SEMANA : " + LocalDate.now().getDayOfWeek());

		

		System.out.println("Data .now() em string : " + dataEmString(LocalDate.now()));

	}

}
