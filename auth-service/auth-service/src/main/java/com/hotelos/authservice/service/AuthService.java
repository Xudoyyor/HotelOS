package com.hotelos.authservice.service;

import com.hotelos.authservice.domain.Employee;
import com.hotelos.authservice.domain.EmployeeFactory;
import com.hotelos.authservice.exceptions.InvalidCredentialsException;
import com.hotelos.authservice.exceptions.UserAlreadyExistsException;
import com.hotelos.authservice.model.LoginRequest;
import com.hotelos.authservice.model.Role;
import com.hotelos.authservice.model.User;
import com.hotelos.authservice.repository.UserRepository;
import com.hotelos.authservice.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public User register(User user) {
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Bunday foydalanuvchi nomi band: " + user.getUsername());
        });

        // OOP: rolga mos Employee avlodi (polimorfizm) - mas'uliyatlarni jurnalga yozamiz.
        Employee employee = EmployeeFactory.create(Role.valueOf(user.getRole()), user.getUsername());
        log.info("Yangi xodim ro'yxatdan o'tdi -> {}", employee.describeAccess());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.login())
                .orElseThrow(() -> new InvalidCredentialsException("Login yoki parol noto'g'ri."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Login yoki parol noto'g'ri.");
        }
        return tokenProvider.createToken(user.getUsername(), user.getRole());
    }
}