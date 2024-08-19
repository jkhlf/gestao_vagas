package br.com.khlf.gestao_vagas.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.khlf.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.khlf.gestao_vagas.modules.company.entities.JobEntity;
import br.com.khlf.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/company/job")
public class JobController {



    @Autowired
    private CreateJobUseCase CreateJobUseCase;


    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create (@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){

        var companyId =  request.getAttribute("company_id");
        var jobEntity = JobEntity.builder()
        .benifits(createJobDTO.getBenefits())
        .companyId(UUID.fromString(companyId.toString()))
        .description(createJobDTO.getDescription())
        .level(createJobDTO.getLevel())
        .build()
        ;

        return this.CreateJobUseCase.execute(jobEntity);
    }
}
