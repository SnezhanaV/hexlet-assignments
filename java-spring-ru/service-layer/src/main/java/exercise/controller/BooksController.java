package exercise.controller;

import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BookDTO>> index() {
        var books = bookService.getAll();
        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @PostMapping(path = "")
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookCreateDTO bookData) {
        var book = bookService.create(bookData);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> show(@PathVariable Long id) {
        var book = bookService.findById(id);
        return ResponseEntity.of(book);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDTO> update(@Valid @RequestBody BookUpdateDTO bookData, @PathVariable Long id) {
        var book = bookService.update(bookData, id);
        return ResponseEntity.of(book);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
    // END
}
