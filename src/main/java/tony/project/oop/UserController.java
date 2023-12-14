package tony.project.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            if (loggedInUser.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/addMovie")
    public ResponseEntity<User> addMovieToUser(@RequestBody Map<String, String> payload) {
        ObjectId userId = new ObjectId(payload.get("userId"));
        String imdbId = payload.get("imdbId");
        User updatedUser = userService.addMovieToUser(userId, imdbId);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeMovie")
    public ResponseEntity<User> removeMovieFromUser(@RequestBody Map<String, String> payload) {
        ObjectId userId = new ObjectId(payload.get("userId"));
        String imdbId = payload.get("imdbId");
        User updatedUser = userService.removeMovieFromUser(userId, imdbId);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId) {
        User user = userService.singleUser(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable ObjectId userId,
            @RequestBody Map<String, String> payload) {
        User updatedUser = userService.updateUser(userId, payload.get("username"), payload.get("email"),
                payload.get("password"), payload.get("oldPassword"));
        Map<String, String> response = new HashMap<>();
        if (updatedUser == null) {
            User optionalUser = userService.singleUser(userId);
            if (optionalUser != null) {
                response.put("message", "Old password is incorrect.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            } else {
                response.put("message", "User not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }
        response.put("message", "User updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}