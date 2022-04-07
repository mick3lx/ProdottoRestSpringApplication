package it.devlecce.verifica.controller;

import it.devlecce.verifica.avviso.ProdottoNonTrovato;
import it.devlecce.verifica.model.Prodotto;
import it.devlecce.verifica.repository.ProdottoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;


@RestController
public class ProdottoRestController {
    private static Logger logger = LoggerFactory.getLogger(ProdottoRestController.class);
    private final ProdottoRepository repository;
    ProdottoRestController(ProdottoRepository repository) {
        this.repository = repository;
    }

    //trova tutti i prodotti
    @GetMapping("/prodotti")
    List<Prodotto> tutti() {
        return repository.findAll();
    }

    //inserisce il prodotto
    @PostMapping("/prodotti")
    Prodotto nuovoProdotto(@RequestBody Prodotto nuovoProdotto) {
        return repository.save(nuovoProdotto);
    }

    //trova i prodotti per ID
    @GetMapping("/prodotti/{id}")
    Prodotto singoloProdotto(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProdottoNonTrovato(id));

    }

    //inserisce il prodotto per ID
    @PutMapping("/prodotti/{id}")
    Prodotto aggiornaProdotto(@RequestBody Prodotto prodotto, @PathVariable Long id){
        return repository.save(prodotto);
    }

    //cancella il prodotto per ID
    @DeleteMapping("/prodotto/{id}")
    void eliminaProdotto(@PathVariable Long id){
        repository.deleteById(id);
    }

    //cerca il prodotto per nome
    @GetMapping("/prodotti/ricerca/nome/{nome}")
    List<Prodotto>cercaPerNome(@PathVariable String nome){
        return repository.findByNome(nome);
    }

    //cerca prodotto per data
    @RequestMapping(path = "/prodotti/ricerca/datadiacquisto",
            method = RequestMethod.GET)
    public List<Prodotto> trovaProdottoPerDataDiAcquisto(
            @RequestParam(name = "datada")
            @DateTimeFormat(pattern = "dd-MM-yyyy")Date from,
            @RequestParam(name = "dataa")
            @DateTimeFormat(pattern = "dd-MM-yyyy")Date to){
        return repository.findByDatadiacquistoBetween(from, to);
    }

    @PostMapping("/caricafile")
    public String caricaFile(@RequestParam("seleziona file") MultipartFile file){
        String infoFile = file.getOriginalFilename() + " - " + file.getContentType();
        String conFormat = String.format("%s-%s", file.getOriginalFilename(), file.getContentType());
        logger.info(infoFile);
        logger.warn(conFormat);
        return conFormat;
    }

    @PutMapping("/prodotto/{id}")
    public Prodotto aggiornaDatiProdotto(@PathVariable Long id, @RequestBody Prodotto prodotto) {
        return  repository.findById(id)
                .map(
                        nuovoProdotto -> {
                            nuovoProdotto.setNome(prodotto.getNome());
                            nuovoProdotto.setPrezzo(prodotto.getPrezzo());
                            return repository.save(nuovoProdotto);
                        }
                )
                .orElseGet(() -> {
                            //il prodotto esiste
                            prodotto.setId(id);
                            return repository.save(prodotto);
                        }
                );
    }





}
