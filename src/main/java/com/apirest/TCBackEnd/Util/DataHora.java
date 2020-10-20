package com.apirest.TCBackEnd.Util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
@Service
public class DataHora {
	
	//private DateTimeFormatter formataHora = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL);
	private DateTimeFormatter formataHora1 = DateTimeFormatter.ofPattern("HH-mm-ss");
	
	private DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public LocalTime stringEmHora(String string) {		
		return LocalTime.from(formataHora1.parse(string));		
	}
	public String horaEmString(LocalTime localtime) {
		return formataHora1.format(localtime);
	}
	
	public LocalDate stringEmData(String string) {
		return LocalDate.from(formataData.parse(string));
	}
	public String dataEmString(LocalDate localDate) {
		return formataData.format(localDate);
	}
	
	@EventListener(ContextRefreshedEvent.class)
	private void teste() {
		System.out.println("INICIANDO TESTE DE HORA DATA");
		//teste String em hora 11-30-33
		System.out.println("String Hora '11-30-33' : "+stringEmHora("11-30-33"));

		// testa HR.now() em string
		System.out.println("LocalTime.now() : "+LocalTime.now());

		System.out.println("Hora de agora .now() em string : "+horaEmString(LocalTime.now()));
		
		
		System.out.println("TESTE DE DATA");
		//teste String em data 2020-02-22
		System.out.println("String Data '2020-02-22' : "+stringEmData("2020-02-22"));
		// testa data.now() em string
		System.out.println("LocalDate.now() : "+LocalDate.now());

		System.out.println("Data .now() em string : "+dataEmString(LocalDate.now()));
	}


}
