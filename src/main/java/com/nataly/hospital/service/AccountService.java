package com.nataly.hospital.service;

import com.nataly.hospital.entity.Account;
import com.nataly.hospital.entity.User;
import com.nataly.hospital.exception.HospitalException;
import com.nataly.hospital.exception.Message;
import com.nataly.hospital.repository.AccountRepository;
import com.nataly.hospital.repository.UserRepository;
import com.nataly.hospital.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByLogin(username).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
    }

    public Account register(Account account) {
        User user = userRepository.findByPhoneNumber(account.getLogin()).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setUser(user);
        return accountRepository.save(account);
    }

    public String auth(String login, String password) {
        Account account = accountRepository.findByLogin(login).orElseThrow(() -> new HospitalException(Message.NOT_FOUND));
        if (passwordEncoder.matches(password, account.getPassword())) {
            return jwtProvider.generateToken(login);
        }
        return null;
    }
}
