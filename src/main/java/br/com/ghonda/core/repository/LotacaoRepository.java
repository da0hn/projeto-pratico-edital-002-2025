package br.com.ghonda.core.repository;

import br.com.ghonda.core.domain.Lotacao;
import br.com.ghonda.core.dto.LotacaoDetailPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {

    @Query(
        value = """
            SELECT l
            FROM Lotacao l
            WHERE (:id IS NULL OR l.id = :id)
            AND (:servidorId IS NULL OR l.pessoa.id = :servidorId)
            AND (:unidadeId IS NULL OR l.unidade.id = :unidadeId)
            AND (:portaria IS NULL OR lower(l.portaria) LIKE lower(concat('%', cast(:portaria as string), '%')))
            AND ( cast(:dataInicioLotacao as date) is null or l.dataLotacao >= :dataInicioLotacao)
            AND ( cast(:dataFimLotacao as date) is null or l.dataLotacao <= :dataFimLotacao)
            AND ( cast(:dataInicioRemocao as date) is null or l.dataRemocao >= :dataInicioRemocao)
            AND ( cast(:dataFimRemocao as date) is null or l.dataRemocao <= :dataFimRemocao)
        """,
        countQuery = """
            SELECT COUNT(l)
            FROM Lotacao l
            WHERE (:id IS NULL OR l.id = :id)
            AND (:servidorId IS NULL OR l.pessoa.id = :servidorId)
            AND (:unidadeId IS NULL OR l.unidade.id = :unidadeId)
            AND (:portaria IS NULL OR lower(l.portaria) LIKE lower(concat('%', cast(:portaria as string), '%')))
            AND ( cast(:dataInicioLotacao as date) is null or l.dataLotacao >= :dataInicioLotacao)
            AND ( cast(:dataFimLotacao as date) is null or l.dataLotacao <= :dataFimLotacao)
            AND ( cast(:dataInicioRemocao as date) is null or l.dataRemocao >= :dataInicioRemocao)
            AND ( cast(:dataFimRemocao as date) is null or l.dataRemocao <= :dataFimRemocao)
        """
    )
    Page<Lotacao> findAllBy(
        @Param("id") Long id,
        @Param("servidorId") Long servidorId,
        @Param("unidadeId") Long unidadeId,
        @Param("portaria") String portaria,
        @Param("dataInicioLotacao") LocalDate dataInicioLotacao,
        @Param("dataFimLotacao") LocalDate dataFimLotacao,
        @Param("dataInicioRemocao") LocalDate dataInicioRemocao,
        @Param("dataFimRemocao") LocalDate dataFimRemocao,
        Pageable pageable
    );

}
