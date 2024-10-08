package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    // BEGIN
    public List<BookDTO> getAll() {
        var book = bookRepository.findAll();
        return book.stream()
                .map(bookMapper::map)
                .toList();
    }

    public BookDTO create(BookCreateDTO bookData) {
        var book = bookMapper.map(bookData);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public Optional<BookDTO> findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        return Optional.ofNullable(bookMapper.map(book));
    }

    public Optional<BookDTO> update(BookUpdateDTO bookData, Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        bookMapper.update(bookData, book);
        bookRepository.save(book);
        return Optional.ofNullable(bookMapper.map(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}
