package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursonRepository extends JpaRepository<Curso, Long > {
    Curso findByNome(String nomeCurso);
}
