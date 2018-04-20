package br.com.desafio.contaazul.rboleto.business;

import br.com.desafio.contaazul.rboleto.entity.Boleto;

import java.util.ArrayList;
import java.util.List;

public class BoletoBusiness {
    public List<Boleto> listarBoletos() {
        List<Boleto> lista = new ArrayList<>();
        Boleto boleto = new Boleto();
        lista.add(boleto);
        return lista;
    }
}
