package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.dto.AuthorDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAll() {
        var author = authorRepository.findAll();
        return author.stream()
                .map(authorMapper::map)
                .toList();
    }

    public Optional<AuthorDTO> findById(long id) {
        var author =  authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        return Optional.ofNullable(authorMapper.map(author));
    }

    public AuthorDTO create(AuthorCreateDTO authorData) {
        var author = authorMapper.map(authorData);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public Optional<AuthorDTO> update(AuthorUpdateDTO authorData, Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        authorMapper.update(authorData, author);
        authorRepository.save(author);
        return Optional.ofNullable(authorMapper.map(author));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
