package dariocecchinato.s19l2_authorization_and_password.controllers;

import dariocecchinato.s19l2_authorization_and_password.entities.Viaggio;
import dariocecchinato.s19l2_authorization_and_password.exceptions.BadRequestException;
import dariocecchinato.s19l2_authorization_and_password.payloads.ViaggioPayloadDTO;
import dariocecchinato.s19l2_authorization_and_password.payloads.ViaggioResponseDTO;
import dariocecchinato.s19l2_authorization_and_password.services.ViaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    @Autowired
    private ViaggiService viaggiService;

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public ViaggioResponseDTO saveViaggio(@RequestBody @Validated ViaggioPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono errore con il payload " + message);
        }else{
            return new ViaggioResponseDTO(this.viaggiService.save(body).getId());
        }
    }

    @GetMapping
    public Page<Viaggio> findAllViaggi(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "10")int size,
                                       @RequestParam(defaultValue = "destinazione")String sortby){
        return this.viaggiService.findAll(page, size, sortby);
    }


    @GetMapping("/{viaggioId}")
    public Viaggio findById (@PathVariable UUID viaggioId){
        return  viaggiService.findById(viaggioId);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio findByIdAndUpdate (@PathVariable UUID viaggioId, @RequestBody ViaggioPayloadDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(" ."));
            throw new BadRequestException("Ci sono stati errori nel payload. " + message);
    }else {
            return this.viaggiService.findByIdAndUpdate(viaggioId,body);
        }
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID viaggioId) {
        this.viaggiService.findByIdAndDelete(viaggioId);

    }

}
