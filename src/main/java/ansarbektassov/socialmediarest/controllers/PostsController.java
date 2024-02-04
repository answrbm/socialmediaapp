package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.PersonDTO;
import ansarbektassov.socialmediarest.dto.PostCreateDTO;
import ansarbektassov.socialmediarest.dto.PostDTO;
import ansarbektassov.socialmediarest.exceptions.post.PostNotCreatedException;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.models.Post;
import ansarbektassov.socialmediarest.services.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Tag(name = "Posts")
public class PostsController {

    private final PostsService postsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<PostDTO> findAll() {
        return postsService.findAll().stream().map(this::convertToPostDTO).toList();
    }

//    @GetMapping("/{postId}")
//    public PostDTO findById(@PathVariable("postId") int postId) {
//        return convertToPostDTO(postsService.findById(postId));
//    }

    @GetMapping("/{personId}")
    public List<PostDTO> findByPersonId(@PathVariable("personId") int personId) {
        return postsService.findByCreator(personId).stream().map(this::convertToPostDTO).toList();
    }

    @PostMapping
    public Map<String,String> createPost(@RequestBody @Validated PostCreateDTO postDTO, BindingResult bindingResult) {
        Post post = convertToPost(postDTO);
        if(bindingResult.hasErrors())
            throw new PostNotCreatedException(buildError(bindingResult.getFieldErrors()));

        postsService.save(post);
        return Map.of("message","created");
    }

    @PutMapping("/{postId}")
    public Map<String,String> updatePost(
            @PathVariable("postId") int postId, @RequestBody @Validated PostCreateDTO postDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new PostNotCreatedException(buildError(bindingResult.getFieldErrors()));
        postsService.update(postId, postDTO);
        return Map.of("message","updated");
    }

    @DeleteMapping("/{postId}")
    public Map<String, String> deletePost(@PathVariable("postId") int postId) {
        postsService.delete(postId);
        return Map.of("message","deleted");
    }

    public PostDTO convertToPostDTO(Post post) {
        PersonDTO creator = convertToPersonDTO(post.getCreator());
        PostDTO postDTO = modelMapper.map(post,PostDTO.class);
        postDTO.setCreator(creator);
        return postDTO;
    }

    public PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    public Post convertToPost(PostCreateDTO postDTO) {
        return modelMapper.map(postDTO,Post.class);
    }

    public String buildError(List<FieldError> fieldErrors) {
        StringBuilder errorMessage = new StringBuilder();
        for(FieldError error : fieldErrors) {
            errorMessage.append(error.getField())
                    .append("-").append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}
