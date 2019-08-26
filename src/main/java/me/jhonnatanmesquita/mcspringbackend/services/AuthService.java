package me.jhonnatanmesquita.mcspringbackend.services;

import me.jhonnatanmesquita.mcspringbackend.exceptions.ObjectNotFoundException;
import me.jhonnatanmesquita.mcspringbackend.models.Cliente;
import me.jhonnatanmesquita.mcspringbackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado!");
        }

        String newPass = newPassword();
        cliente.setSenha(passwordEncoder.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword(){
        char[] vet = new char[32];
        for (int i=0; i < vet.length; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar(){
        int opt = rand.nextInt(3);
        if(opt == 0){
            return (char) (rand.nextInt(10) + 48);
        }else if (opt == 1){
            return (char) (rand.nextInt(26) + 65);
        }else {
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
