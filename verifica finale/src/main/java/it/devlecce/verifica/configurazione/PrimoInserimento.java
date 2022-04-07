package it.devlecce.verifica.configurazione;

import it.devlecce.verifica.model.Prodotto;
import it.devlecce.verifica.repository.ProdottoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Configuration
public class PrimoInserimento {
    private static final Logger logger = LoggerFactory.getLogger(PrimoInserimento.class);


    @Bean
    CommandLineRunner initDatabase(ProdottoRepository repository){
        return args -> {
            logger.info("Primo Inserimento");
            SimpleDateFormat dataAcquisto = new SimpleDateFormat("dd-MM-yyyy");
            Date datadiacquisto1 = dataAcquisto.parse("16-04-2018");
            Date datadiacquisto2 = dataAcquisto.parse("24-09-2018");
            Date datadiacquisto3 = dataAcquisto.parse("31-12-2020");


            Prodotto n1 = new Prodotto("Televisione", 1499, datadiacquisto1);
            Prodotto n2 = new Prodotto("Cellulare", 650, datadiacquisto2);
            Prodotto n3 = new Prodotto("Lavastoviglie", 800, datadiacquisto3);
            List<Prodotto> prodotti = new ArrayList<>();
            prodotti.add(n1);
            prodotti.add(n2);
            prodotti.add(n3);
            repository.saveAll(prodotti);

        };
    }
}
