package br.com.khlf.gestao_vagas.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.khlf.gestao_vagas.modules.candidate.dto.AuthRequestDTO;
import br.com.khlf.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCandidateController {
    

@Autowired
private AuthCandidateUseCase authCandidateUseCase;

@PostMapping("/candidate")
public ResponseEntity <Object> auth(@RequestBody AuthRequestDTO authRequestDTO) {
    try {
        var token = this.authCandidateUseCase.execute(authRequestDTO);
        return ResponseEntity.ok().body(token);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}

}
