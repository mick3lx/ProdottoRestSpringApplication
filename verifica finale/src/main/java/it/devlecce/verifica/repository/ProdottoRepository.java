package it.devlecce.verifica.repository;

import it.devlecce.verifica.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

}
