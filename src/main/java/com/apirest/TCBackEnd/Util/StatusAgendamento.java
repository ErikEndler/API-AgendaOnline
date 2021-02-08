package com.apirest.TCBackEnd.Util;

public enum StatusAgendamento {
	PENDENTE, // agendamento solicitado mas ainda não confirmado
	ATENDIDO, // agendamento que foi antendido
	CANCELADO, // agendamwnto cabcelado pelo cliente ou funcionario
	FALTOU, // quando o funcionario atribui que paciente não compareceu
	AGENDADO, // agendamento que foi confirmado
	NAOATENDIDO // agendamento confirmado que ainda não foi lançado com oatendido pelo sistema
}