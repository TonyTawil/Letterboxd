package tony.project.oop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private ObjectId id;
    private String body;
    private ObjectId userId;
    private String username;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Review(String body, ObjectId userId, String username, LocalDateTime created, LocalDateTime updated) {
        this.body = body;
        this.userId = userId;
        this.username = username;
        this.created = created;
        this.updated = updated;
    }
}