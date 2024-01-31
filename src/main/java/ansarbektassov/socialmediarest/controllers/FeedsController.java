package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.PostDTO;
import ansarbektassov.socialmediarest.services.FeedsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@AllArgsConstructor
@Tag(name = "Feeds")
public class FeedsController {

    private final FeedsService feedsService;

    @GetMapping
    public List<PostDTO> findLatestFeeds() {
        return feedsService.findAll();
    }
}
