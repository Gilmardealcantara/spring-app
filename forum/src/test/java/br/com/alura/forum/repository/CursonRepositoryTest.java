package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // user real db
@ActiveProfiles("test")
public class CursonRepositoryTest {

    @Autowired
    private CursonRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    public void ShouldBegetCursoByName(){
        String nomeCurso = "HTML 5";

        Curso cursoHtml5 = new Curso();
        cursoHtml5.setNome(nomeCurso);
        cursoHtml5.setCategoria("prog");
        em.persist(cursoHtml5);

        Curso curso = repository.findByNome(nomeCurso);
        Assertions.assertNotNull(curso);
    }

    @Test
    public void ShouldNotGetCursoByNameWhenNotExists(){
        String nomeCurso = "AAAA";
        Curso curso = repository.findByNome(nomeCurso);
        Assertions.assertNull(curso);
    }
}