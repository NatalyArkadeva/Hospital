package com.nataly.hospital.dto;

import com.nataly.hospital.entity.Account;
import lombok.Data;

@Data
public class RegistrationDto {
    private String login;
    private String password;

    public Account toAccount() {
        Account account = new Account();
        account.setLogin(this.login);
        account.setPassword(this.password);
        return account;
    }
}
