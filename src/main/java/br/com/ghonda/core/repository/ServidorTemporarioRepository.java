package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.ServidorTemporario;
import br.com.ghonda.core.domain.UF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, Long> {

    @Query(value = """
                   select distinct temporario
                   from ServidorTemporario temporario
                      inner join temporario.enderecos enderecos
                      inner join enderecos.cidade cidade
                   where (:nome is null or temporario.nome like lower(concat('%', cast(:nome as string), '%'))) and
                      (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
                      (:uf is null or cidade.uf = :uf)
                   """,
           countQuery = """
                        select count(distinct temporario)
                        from ServidorTemporario temporario
                           inner join temporario.enderecos enderecos
                           inner join enderecos.cidade cidade
                        where (:nome is null or temporario.nome like lower(concat('%', cast(:nome as string), '%'))) and
                           (:nomeCidade is null or cidade.nome like lower(concat('%', cast(:nomeCidade as string), '%'))) and
                           (:uf is null or cidade.uf = :uf)
                        """)
    Page<ServidorTemporario> findAll(
        String nome,
        String nomeCidade,
        UF uf,
        Pageable pageable
    );

}
