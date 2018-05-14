package com.mateus.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.mateus.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pcb, Date instanteDoPedido) {
		Calendar c = Calendar.getInstance();
		c.setTime(instanteDoPedido);
		c.add(Calendar.DAY_OF_MONTH, 7);
		pcb.setDataVencimento(c.getTime());
	}
}
