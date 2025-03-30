package br.com.ghonda.core.domain;

import br.com.ghonda.core.dto.NewUnidadePayload;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidade")
public class Unidade implements Serializable {

    @Serial
    private static final long serialVersionUID = -1200907729180588102L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unid_id", nullable = false)
    private Long id;

    @Column(name = "unid_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "unid_sigla", length = 20, nullable = false)
    private String sigla;

    @ManyToMany
    @JoinTable(
        name = "unidade_endereco",
        joinColumns = @JoinColumn(name = "unid_id"),
        inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    @Builder.Default
    private Set<Endereco> enderecos = new HashSet<>(0);

    public static Unidade of(final NewUnidadePayload payload) {
        return Unidade.builder()
            .id(null)
            .nome(payload.nome())
            .sigla(payload.sigla())
            .build();
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
            ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : this.getClass().hashCode();
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null) return false;
        final Class<?> oEffectiveClass = o instanceof HibernateProxy ?
            ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        final Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
            ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        final Unidade unidade = (Unidade) o;
        return this.id != null && Objects.equals(this.id, unidade.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", this.id)
            .append("nome", this.nome)
            .append("sigla", this.sigla)
            .toString();
    }

    public void addEndereco(final Endereco endereco) {
        this.enderecos.add(endereco);
        if (!endereco.getUnidades().contains(this)) {
            endereco.addUnidade(this);
        }
    }

}
