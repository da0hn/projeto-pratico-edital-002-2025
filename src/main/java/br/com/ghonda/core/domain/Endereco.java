package br.com.ghonda.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "endereco")
public class Endereco implements Serializable {

    @Serial
    private static final long serialVersionUID = 577673114173781228L;

    @Id
    @Column(name = "end_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "end_logradouro", length = 200)
    private String logradouro;

    @Column(name = "end_tipo_logradouro", length = 50)
    private String tipoLogradouro;

    @Column(name = "end_numero")
    private Long numero;

    @Column(name = "end_bairro", length = 100)
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;

    @Builder.Default
    @ManyToMany(mappedBy = "enderecos")
    private Set<Pessoa> pessoas = new HashSet<>(0);

    public void addPessoa(final Pessoa pessoa) {
        this.pessoas.add(pessoa);
        if (!pessoa.getEnderecos().contains(this)) {
            pessoa.addEndereco(this);
        }
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
        final Endereco endereco = (Endereco) o;
        return this.id != null && Objects.equals(this.id, endereco.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", this.id)
            .append("logradouro", this.logradouro)
            .append("tipoLogradouro", this.tipoLogradouro)
            .append("numero", this.numero)
            .append("bairro", this.bairro)
            .append("cidade", this.cidade)
            .toString();
    }

}
