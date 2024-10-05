package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<PostDTO> index() {
        return postRepository.findAll().stream().map(this::toPostDTO).toList();
    }

    @GetMapping(path = "/{id}")
    public PostDTO show(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return toPostDTO(post);
    }

    private PostDTO toPostDTO(Post data) {
        PostDTO post = new PostDTO();
        post.setId(data.getId());
        post.setTitle(data.getTitle());
        post.setBody(data.getBody());
        List<CommentDTO> comments = commentRepository.findByPostId(data.getId()).stream()
                .map(this::toCommentDTO).toList();
        post.setComments(comments);
        return post;
    }

    private CommentDTO toCommentDTO(Comment data) {
        CommentDTO comment = new CommentDTO();
        comment.setId(data.getId());
        comment.setBody(data.getBody());
        return comment;
    }
}
// END
