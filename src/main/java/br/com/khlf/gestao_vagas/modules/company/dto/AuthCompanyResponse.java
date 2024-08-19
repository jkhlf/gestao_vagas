package br.com.khlf.gestao_vagas.modules.company.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCompanyResponse {
    private String access_token;
    private String expire_in;
}
