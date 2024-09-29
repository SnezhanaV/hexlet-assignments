package exercise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPostsWithParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().skip((page - 1) * limit).limit(limit).collect(Collectors.toList());
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> getPost(@PathVariable String id) {
        var findedPost = posts.stream().filter(f -> f.getId().equals(id)).findFirst();
        if (findedPost.isPresent()) {
            return ResponseEntity.ok(findedPost);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post newPost = new Post(post.getId(), post.getTitle(), post.getBody());
        posts.add(newPost);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPost);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        var findedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (findedPost.isPresent()) {
            Post updatedPost = findedPost.get();
            updatedPost.setId(post.getId());
            updatedPost.setTitle(post.getTitle());
            updatedPost.setBody(post.getBody());
            return ResponseEntity
                    .ok()
                    .body(post);
        }
        return ResponseEntity
                .status(HttpStatusCode.valueOf(204))
                .body(post);
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
