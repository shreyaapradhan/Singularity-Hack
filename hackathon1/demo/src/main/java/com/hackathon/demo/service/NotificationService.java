package com.hackathon.demo.service;

import com.hackathon.demo.model.TrustedContact;
import com.hackathon.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    public void notifyContacts(User user,
                               List<TrustedContact> contacts,
                               String message) {

        for (TrustedContact contact : contacts) {

            // 🔔 For now, simulate notification
            System.out.println(
                    "📢 ALERT SENT to "
                            + contact.getName()
                            + " (" + contact.getPhone() + ")"
                            + " | Priority: " + contact.getPriorityOrder()
                            + " | Message: " + message
            );
        }
    }
}
