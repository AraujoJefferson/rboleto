package br.com.desafio.contaazul.rbankslip.business;

import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankslipRepository extends CrudRepository<Bankslip,UUID> {
}
