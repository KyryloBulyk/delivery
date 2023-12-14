package com.delivery.restaurant.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userJpaRepository;

    @Autowired
    public UserService(UserRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }
}
