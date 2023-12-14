package tony.project.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User singleUser(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addMovieToUser(ObjectId id, String imdbId) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.getWatchedMovies().add(imdbId);
            return userRepository.save(user);
        }
        return null;
    }

    public User updateUser(ObjectId id, String username, String email, String password, String oldPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(oldPassword)) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(username);
            existingUser.setEmail(email);
            existingUser.setPassword(password);
            return userRepository.save(existingUser);
        }
        return null;
    }

    public User removeMovieFromUser(ObjectId id, String imdbId) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.getWatchedMovies().remove(imdbId);
            return userRepository.save(user);
        }
        return null;
    }
}
