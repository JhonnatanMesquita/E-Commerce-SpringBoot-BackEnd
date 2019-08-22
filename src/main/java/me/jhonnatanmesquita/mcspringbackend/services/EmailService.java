package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.models.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}
