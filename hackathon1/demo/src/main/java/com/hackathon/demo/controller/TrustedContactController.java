package com.hackathon.demo.controller;

import com.hackathon.demo.model.TrustedContact;
import com.hackathon.demo.service.TrustedContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class TrustedContactController {

    private final TrustedContactService trustedContactService;

    public TrustedContactController(TrustedContactService trustedContactService) {
        this.trustedContactService = trustedContactService;
    }

    @PostMapping("/{userId}")
    public TrustedContact addContact(
            @PathVariable Long userId,
            @RequestBody TrustedContact contact) {

        return trustedContactService.addContact(userId, contact);
    }

    @GetMapping("/{userId}")
    public List<TrustedContact> getContacts(@PathVariable Long userId) {
        return trustedContactService.getContacts(userId);
    }
}
