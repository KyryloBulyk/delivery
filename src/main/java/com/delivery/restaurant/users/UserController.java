package com.delivery.restaurant.users;

import com.delivery.restaurant.authenticate.AuthRequest;
import com.delivery.restaurant.authenticate.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Create a user
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);

        if(createdUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(createdUser);
    }

    //Changing properties of a user
    @PutMapping("/changing/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    //Deleting a user
    @DeleteMapping("/deleting/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//        );
//
//        if(authentication.isAuthenticated()) {
//            String jwtToken = jwtService.generateToken(authRequest.getUsername());
//
//            // Створення HTTP-тільки кукі
//            ResponseCookie cookie = ResponseCookie.from("jwtToken", jwtToken) // назва кукі
//                    .httpOnly(true)   // встановлення кукі як HTTP-тільки
//                    .secure(true)    // використання secure, якщо ви працюєте з HTTPS
//                    .path("/")       // шлях, для якого буде доступний кукі
//                    .maxAge(60 * 60) // тривалість життя кукі у секундах
//                    .build();
//
//            response.addHeader("Set-Cookie", cookie.toString());
//
//            // Можете повернути додаткову інформацію, якщо потрібно
//            return ResponseEntity.ok().body("User authenticated successfully");
//        } else {
//            throw new UsernameNotFoundException("Invalid user request");
//        }
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//        );
//
//        if(authentication.isAuthenticated()) {
//            String jwtToken = jwtService.generateToken(authRequest.getUsername());
//
//            User user = userService.findByEmail(authRequest.getUsername())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//            AuthResponse authResponse = new AuthResponse(jwtToken, user.getId());
//            return ResponseEntity.ok(authResponse);
//        } else {
//            throw new UsernameNotFoundException("Invalid user request");
//        }
//    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
