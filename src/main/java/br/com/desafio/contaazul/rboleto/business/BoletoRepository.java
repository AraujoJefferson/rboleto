package br.com.desafio.contaazul.rboleto.business;

import br.com.desafio.contaazul.rboleto.entity.Boleto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletoRepository extends CrudRepository<Boleto,String> {
}
