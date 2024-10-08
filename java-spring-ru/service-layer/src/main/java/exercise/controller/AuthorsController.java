package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.dto.AuthorDTO;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AuthorDTO>> index() {
        var authors = authorService.getAll();
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(authors.size()))
                .body(authors);
    }

    @PostMapping(path = "")
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorCreateDTO authorData) {
        var author = authorService.create(authorData);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> show(@PathVariable Long id) {
        var author = authorService.findById(id);
        return ResponseEntity.of(author);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> update(@Valid @RequestBody AuthorUpdateDTO authorData, @PathVariable Long id) {
        var author = authorService.update(authorData, id);
        return ResponseEntity.of(author);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
    // END
}
