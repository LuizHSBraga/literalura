package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Livro(@JsonAlias("id") String id,
                    @JsonAlias("title") String title,
                    @JsonAlias("authors") List<Autor> autores,
                    @JsonAlias("languages") List<String> languages,
                    @JsonAlias("download_count") Integer download
) {

}