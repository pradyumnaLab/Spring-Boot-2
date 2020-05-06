package com.pk.PhotoAppApiAccountManagement.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/accounts")
    public String getAccounts(){
        return "I am accounts";
    }
}
