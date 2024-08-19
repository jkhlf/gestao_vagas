package br.com.khlf.gestao_vagas.modules.candidate.useCases;

import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.khlf.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.khlf.gestao_vagas.modules.candidate.dto.AuthCandidateResponse;
import br.com.khlf.gestao_vagas.modules.candidate.dto.AuthRequestDTO;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;
    
    @Autowired
    private CandidateRepository candidateRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthCandidateResponse execute(AuthRequestDTO authRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authRequestDTO.username())
            .orElseThrow(()-> {
            throw new UsernameNotFoundException("Username/password incorrect");
        });
        
        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.password(), candidate.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(java.time.Duration.ofMinutes(10));
        var token = JWT.create()
        .withIssuer("Teste")
        .withSubject(candidate.getId().toString())
        .withExpiresAt(Instant.now().plus(java.time.Duration.ofMinutes(10)))
        .withClaim("roles",Arrays.asList("CANDIDATE"))
        .sign(algorithm);
        

        var authCandidateResponse = AuthCandidateResponse.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

        return authCandidateResponse;
    }   

}
