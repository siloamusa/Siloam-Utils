package com.siloamusa.utils.services;

import com.siloamusa.utils.dto.EmailDetails; // Import your EmailDetails class

public interface EmailService {
    void sendEmailAsync(EmailDetails details);
}
