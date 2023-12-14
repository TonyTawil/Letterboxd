package tony.project.oop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String email;
    private String password;
    private List<String> watchedMovies;

    public User(String username, String email, String password, List<String> watchedMovies, List<ObjectId> reviews) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.watchedMovies = watchedMovies;
    }
}