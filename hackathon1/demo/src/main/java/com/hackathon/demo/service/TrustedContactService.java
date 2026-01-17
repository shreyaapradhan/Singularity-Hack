package com.hackathon.demo.service;

import com.hackathon.demo.model.TrustedContact;
import com.hackathon.demo.model.User;
import com.hackathon.demo.repository.TrustedContactRepository;
import com.hackathon.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustedContactService {

    private final TrustedContactRepository trustedContactRepository;
    private final UserRepository userRepository;

    public TrustedContactService(TrustedContactRepository trustedContactRepository,
                                 UserRepository userRepository) {
        this.trustedContactRepository = trustedContactRepository;
        this.userRepository = userRepository;
    }

    public TrustedContact addContact(Long userId, TrustedContact contact) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        contact.setUser(user);
        return trustedContactRepository.save(contact);
    }

    public List<TrustedContact> getContacts(Long userId) {
        return trustedContactRepository
                .findByUserIdOrderByPriorityOrderAsc(userId);
    }
}
