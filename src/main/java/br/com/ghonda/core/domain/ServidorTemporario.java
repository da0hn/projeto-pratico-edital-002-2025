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
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "servidor_temporario")
public class ServidorTemporario extends Pessoa {

    @Serial
    private static final long serialVersionUID = -5360804500366701931L;

    @Column(name = "st_data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate dataDemissao;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("dataAdmissao", this.dataAdmissao)
            .append("dataDemissao", this.dataDemissao)
            .toString();
    }

}
