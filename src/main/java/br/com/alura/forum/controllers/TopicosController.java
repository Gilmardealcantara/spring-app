package br.com.alura.forum.controllers;

import br.com.alura.forum.controllers.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controllers.dto.TopicoDto;
import br.com.alura.forum.controllers.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controllers.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursonRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursonRepository cursonRepository;

    @GetMapping
    public Page<TopicoDto> lista(@RequestParam(required = false) String courseName, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (courseName == null) {
            Page<Topico> pages = topicoRepository.findAll(pageable);
            return TopicoDto.converter(pages);
        } else {
            Page<Topico> pages = topicoRepository.findByCursoNome(courseName, pageable);
            return TopicoDto.converter(pages);
        }
    }

    @PostMapping
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursonRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new DetalhesDoTopicoDto(optional.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        Topico topico = form.atualizar(id, topicoRepository);
        return ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
