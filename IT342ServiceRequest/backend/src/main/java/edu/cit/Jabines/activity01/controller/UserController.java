package edu.cit.Jabines.activity01.controller;

import edu.cit.Jabines.activity01.model.User;
import edu.cit.Jabines.activity01.repository.UserRepository;
import edu.cit.Jabines.activity01.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5174"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        try {
            UserPrincipal principal = (UserPrincipal) authentication.getDetails();
            Long userId = principal.getUserId();

            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(user.get());

        } catch (Exception e) {
            log.error("Error retrieving user profile", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);

            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(user.get());

        } catch (Exception e) {
            log.error("Error retrieving user", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
