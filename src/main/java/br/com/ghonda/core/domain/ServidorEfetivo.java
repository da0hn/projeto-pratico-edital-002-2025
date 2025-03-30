package br.com.ghonda.core.domain;

import br.com.ghonda.core.dto.NewServidorEfetivoPayload;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "servidor_efetivo")
public class ServidorEfetivo extends Pessoa {

    @Serial
    private static final long serialVersionUID = 6742956298751141072L;

    @Column(name = "se_matricula", length = 20)
    private String matricula;

    public static ServidorEfetivo of(final NewServidorEfetivoPayload payload) {
        return ServidorEfetivo.builder()
            .id(null)
            .matricula(payload.matricula())
            .nome(payload.pessoa().nome())
            .nomeMae(payload.pessoa().nomeMae())
            .nomePai(payload.pessoa().nomePai())
            .sexo(payload.pessoa().sexo())
            .dataNascimento(payload.pessoa().dataNascimento())
            .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("matricula", this.matricula)
            .toString();
    }

}
