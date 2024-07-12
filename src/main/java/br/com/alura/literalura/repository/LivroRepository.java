package br.com.alura.literalura.repository;

import br.com.alura.literalura.entity.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    @Query("SELECT l FROM LivroEntity l WHERE l.idioma >= :idioma")
    List<LivroEntity> findForLanguaje(String idioma);

}