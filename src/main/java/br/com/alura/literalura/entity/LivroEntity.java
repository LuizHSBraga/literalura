package br.com.alura.literalura.entity;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.util.CadeiaUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "Livro")
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Integer downloads;
    @OneToOne(mappedBy = "livros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AutorEntity autor;

    public LivroEntity() {

    }

    public LivroEntity(Livro livro) {
        this.titulo = CadeiaUtil.limitarLongitud(livro.title(), 200);
        this.downloads = livro.download();
        if (!livro.languages().isEmpty())
            this.idioma = livro.languages().get(0);
        if (!livro.autores().isEmpty()) {
            for (Autor autor : livro.autores()) {
                this.autor = new AutorEntity(autor);
                break;
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "LivroEntity [id=" + id + ", titulo=" + titulo + ", idioma=" + idioma + ", downloads=" + downloads
                + ", autores=" + autor + "]";
    }

    public AutorEntity getAutor() {
        return autor;
    }

    public void setAutor(AutorEntity autor) {
        this.autor = autor;
    }

}