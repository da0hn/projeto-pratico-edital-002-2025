package br.com.ghonda.core.dto;

import br.com.ghonda.core.annotations.Enumerator;
import br.com.ghonda.core.domain.Pessoa;
import br.com.ghonda.core.domain.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Jacksonized
public record PessoaDetailPayload(
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento informada não é válida")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento,
    @Enumerator(message = "Sexo informado não é válido", enumClass = Sexo.class)
    Sexo sexo,
    @NotBlank(message = "Nome da mãe é obrigatório")
    String nomeMae,
    @NotBlank(message = "Nome do pai é obrigatório")
    String nomePai,
    @Valid
    @NotEmpty(message = "Pelo menos um endereço deve ser informado")
    Set<EnderecoDetailPayload> enderecos
) {

    public static PessoaDetailPayload of(final Pessoa pessoa) {
        return new PessoaDetailPayload(
            pessoa.getNome(),
            pessoa.getDataNascimento(),
            pessoa.getSexo(),
            pessoa.getNomeMae(),
            pessoa.getNomePai(),
            pessoa.getEnderecos().stream()
                .map(EnderecoDetailPayload::of)
                .collect(Collectors.toSet())
        );
    }

}
