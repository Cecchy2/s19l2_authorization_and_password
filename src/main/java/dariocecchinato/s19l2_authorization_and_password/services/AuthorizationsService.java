package dariocecchinato.s19l2_authorization_and_password.services;

import dariocecchinato.s19l2_authorization_and_password.entities.Dipendente;
import dariocecchinato.s19l2_authorization_and_password.exceptions.UnauthorizedException;
import dariocecchinato.s19l2_authorization_and_password.payloads.DipendentiLoginDTO;
import dariocecchinato.s19l2_authorization_and_password.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationsService {
    @Autowired
    private DipendentiService dipendentiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiEGeneraToken(DipendentiLoginDTO body){
        Dipendente found = this.dipendentiService.findDipendenteByUsername(body.username());
        if (bcrypt.matches(body.password(), found.getPassword())){
            return this.jwtTools.createToken(found);
        } else{
            throw new UnauthorizedException("Errore nelle credenziali che hai fornito");
        }
    }
}
