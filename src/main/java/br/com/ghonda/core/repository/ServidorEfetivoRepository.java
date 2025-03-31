package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.ServidorEfetivo;
import br.com.ghonda.core.domain.UF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long> {

    @Query(value = """
           select distinct efetivo
           from ServidorEfetivo efetivo
              inner join efetivo.enderecos enderecos
              inner join enderecos.cidade cidade
           where (:nome is null or efetivo.nome like lower(concat('%', cast(:nome as string), '%'))) and
              (:matricula is null or efetivo.matricula like lower(concat('%', cast(:matricula as string), '%'))) and
              (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
              (:uf is null or cidade.uf = :uf)
           """,
           countQuery = """
           select count(distinct efetivo)
           from ServidorEfetivo efetivo
              inner join efetivo.enderecos enderecos
              inner join enderecos.cidade cidade
           where (:nome is null or efetivo.nome like lower(concat('%', cast(:nome as string), '%'))) and
              (:matricula is null or efetivo.matricula like lower(concat('%', cast(:matricula as string), '%'))) and
              (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
              (:uf is null or cidade.uf = :uf)
           """)
    Page<ServidorEfetivo> findAllBy(
        String nome,
        String matricula,
        String nomeCidade,
        UF uf,
        Pageable pageable
    );

}
