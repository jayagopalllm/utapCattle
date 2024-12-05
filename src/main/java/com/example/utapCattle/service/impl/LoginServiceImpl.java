package com.example.utapCattle.service.impl;

import com.example.utapCattle.model.entity.Login;
import com.example.utapCattle.service.LoginService;
import com.example.utapCattle.service.repository.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public boolean validateUserCredentials(String username, String password) {
        Optional<Login> loginOptional = loginRepository.findByUsername(username);

        if (loginOptional.isPresent()) {
            Login login = loginOptional.get();
            return login.getPassword().equals(password);
        }

        return false;
    }
}
