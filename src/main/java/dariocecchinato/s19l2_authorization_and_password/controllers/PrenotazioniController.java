package dariocecchinato.s19l2_authorization_and_password.controllers;

import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import dariocecchinato.s19l2_authorization_and_password.entities.Prenotazione;
import dariocecchinato.s19l2_authorization_and_password.entities.Viaggio;
import dariocecchinato.s19l2_authorization_and_password.exceptions.BadRequestException;
import dariocecchinato.s19l2_authorization_and_password.exceptions.NotFoundException;
import dariocecchinato.s19l2_authorization_and_password.payloads.PrenotazionePayloadDTO;
import dariocecchinato.s19l2_authorization_and_password.repositories.DipendentiReporitory;
import dariocecchinato.s19l2_authorization_and_password.repositories.ViaggiRepository;
import dariocecchinato.s19l2_authorization_and_password.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;
    @Autowired
    private ViaggiRepository viaggiRepository;
    @Autowired
    private DipendentiReporitory dipendentiReporitory;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazionePayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Errore nel payload " + message);
        }else{
            return this.prenotazioniService.creaPrenotazione(body);
        }
    }

    @GetMapping
    public Page<Prenotazione> getAllPrenotazioni (@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy){
        return  this.prenotazioniService.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getById(@PathVariable UUID prenotazioneId){
        return this.prenotazioniService.findById(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    public Prenotazione getByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody @Validated PrenotazionePayloadDTO body){
        Viaggio viaggio = viaggiRepository.findById(body.viaggio_id()).orElseThrow(()-> new NotFoundException(body.viaggio_id()));
        Dipendente dipendente = dipendentiReporitory.findById(body.dipendente_id()).orElseThrow(()-> new NotFoundException(body.dipendente_id()));
        if (prenotazioniService.esistePrenotazionePerGiornoEDipendente(dipendente.getId(), viaggio.getDataViaggio(), prenotazioneId)) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per un viaggio in questa data.");
        }
        Prenotazione found = this.prenotazioniService.findById(prenotazioneId);
        found.setDettagli(body.dettagli());
        found.setViaggio(viaggio);
        found.setDipendente(dipendente);
        return prenotazioniService.updatePrenotazione(found);

    }
    @DeleteMapping("/{prenotazioneId}")
    public void getByIdAndDelete(@PathVariable UUID prenotazioneId){
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }

}
