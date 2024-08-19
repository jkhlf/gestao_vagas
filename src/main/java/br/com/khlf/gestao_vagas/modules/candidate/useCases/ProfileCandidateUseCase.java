package br.com.khlf.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.khlf.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.khlf.gestao_vagas.modules.candidate.dto.ProfileCandidateResponde;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;
    

    public ProfileCandidateResponde execute(UUID idCandidato){
       var candidate = this.candidateRepository.findById(idCandidato)
       .orElseThrow(() -> {
        throw new UsernameNotFoundException("User not found");
       });


       var candidateDTO = ProfileCandidateResponde.builder()
       .description(candidate.getDescription())
       .username(candidate.getUsername())
       .email(candidate.getEmail())
       .name(candidate.getName())
       .id(candidate.getId())
       .build();

       return candidateDTO;
    }

}
