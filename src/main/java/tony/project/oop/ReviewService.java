package tony.project.oop;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, ObjectId userId, String username, String imdbId) {
        Review review = new Review(reviewBody, userId, username, LocalDateTime.now(), LocalDateTime.now());

        reviewRepository.save(review);

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviews").value(review))
                .first();

        return review;
    }

    public void deleteReview(ObjectId id) {
        reviewRepository.deleteById(id);
    }

    public Review updateReview(ObjectId id, String reviewBody) {
        Review review = reviewRepository.findById(id).orElseThrow();
        review.setBody(reviewBody);
        review.setUpdated(LocalDateTime.now());
        reviewRepository.save(review);
        return review;
    }
}
