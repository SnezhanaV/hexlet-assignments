package exercise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    List<Post> getPostsWithParams(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "0") Integer limit) {
        var deleteAfter = (limit == 0) ? posts.size() : limit;
        var deleteBefore = page * limit;
        return posts.stream().skip(deleteBefore).limit(deleteAfter).collect(Collectors.toList());
    }

    @GetMapping("/posts/{id}")
    Optional<Post> getPost(@PathVariable String id) {
        return posts.stream().filter(f -> f.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    Post createPost(@RequestBody Post post) {
        Post newPost = new Post(post.getId(), post.getTitle(), post.getBody());
        posts.add(newPost);
        return newPost;
    }

    @PutMapping("/posts/{id}")
    Post updatePost(@PathVariable String id, @RequestBody Post post) {
        var findedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (findedPost.isPresent()) {
            Post updatedPost = findedPost.get();
            updatedPost.setId(post.getId());
            updatedPost.setTitle(post.getTitle());
            updatedPost.setBody(post.getBody());
        }
        return post;
    }

    @DeleteMapping("/posts/{id}")
    void deletePost(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
