package tony.project.oop;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin("http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Review>(
                reviewService.createReview(payload.get("reviewBody"), new ObjectId(payload.get("userId")),
                        payload.get("username"),
                        payload.get("imdbId")),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable ObjectId id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable ObjectId id, @RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(
                reviewService.updateReview(id, payload.get("reviewBody")),
                HttpStatus.OK);
    }

}