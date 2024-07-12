package br.com.alura.literalura.repository;

import br.com.alura.literalura.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    @Query("SELECT a FROM AutorEntity a WHERE :ano between a.dataNascimento AND a.dataFalecimento")
    List<AutorEntity> findForYear(int ano);

}