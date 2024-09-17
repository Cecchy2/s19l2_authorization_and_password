package dariocecchinato.s19l2_authorization_and_password.services;


import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import dariocecchinato.s19l2_authorization_and_password.exceptions.BadRequestException;
import dariocecchinato.s19l2_authorization_and_password.exceptions.NotFoundException;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendentePayloadDTO;
import dariocecchinato.s19l2_authorization_and_password.repositories.DipendentiReporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DipendentiService {
    @Autowired
    private DipendentiReporitory dipendentiReporitory;
    @Autowired
    private PasswordEncoder bcrypt;


    public Page<Dipendente> findAll(int page, int size, String sortby){
        if (page > 10) page = 10;
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortby));
        return dipendentiReporitory.findAll(pageable);
    }

    public Dipendente save (DipendentePayloadDTO body){
        if (dipendentiReporitory.existsByEmail(body.email()))throw new BadRequestException("L' email " + body.email() + " è già in uso");
        String avatar = "https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome();
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), avatar, bcrypt.encode(body.password()));
        return dipendentiReporitory.save(newDipendente);
    }
    public Dipendente findDipendenteById(UUID dipendenteId){
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        return found;
    }
    public Dipendente findDipendenteByUsername(String username){
        return this.dipendentiReporitory.findByUsername(username).orElseThrow(()-> new NotFoundException(username));

    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, DipendentePayloadDTO body){
        String avatar = "https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome();
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        found.setAvatar(avatar);
        found.setUsername(body.username());
        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        return dipendentiReporitory.save(found);
    }
    public void findByIdAndDeleteDipendente (UUID dipendenteId){
        Dipendente found = this.dipendentiReporitory.findById(dipendenteId).orElseThrow(()->new NotFoundException(dipendenteId));
        if (found == null)throw new NotFoundException(dipendenteId);
        this.dipendentiReporitory.delete(found);
    }



    public Dipendente saveDipendente(Dipendente dipendente) {
        return this.dipendentiReporitory.save(dipendente);
    }
}
