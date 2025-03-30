package br.com.ghonda.core.domain;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("matricula", this.matricula)
            .toString();
    }

}
