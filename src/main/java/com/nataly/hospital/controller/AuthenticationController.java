package com.nataly.hospital.controller;

import com.nataly.hospital.dto.AuthDto;
import com.nataly.hospital.dto.RegistrationDto;
import com.nataly.hospital.entity.Account;
import com.nataly.hospital.service.AccountService;
import com.nataly.hospital.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationDto registrationDto) {
        Account account = accountService.register(registrationDto.toAccount());
        notificationService.sendMail(account.getUser().getEmail(), "Регистрация", "Регистрация прошла успешно");
        return ResponseEntity.status(HttpStatus.OK).body("Account was successfully added");
    }

    @PostMapping("/auth")
    public String auth(@RequestBody AuthDto authDto) {
        return accountService.auth(authDto.getLogin(), authDto.getPassword());
    }

    @GetMapping("/me")
    public Account me(@AuthenticationPrincipal Account account) {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
