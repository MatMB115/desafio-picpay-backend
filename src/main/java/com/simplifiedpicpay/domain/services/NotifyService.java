package com.simplifiedpicpay.domain.services;

import com.simplifiedpicpay.domain.dtos.NotificationDTO;
import com.simplifiedpicpay.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotifyService {
    //@Autowired
    //private RestTemplate restTemplate;

    //@Value("${picpay.notify}")
    //private String notifyUrl;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        //ResponseEntity<String> notificationResponse = restTemplate.postForEntity(notifyUrl, notificationRequest, String.class);
        //if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
        //    System.out.println("Erro ao enviar solicitação");
        //    throw new Exception("Serviço de notificação fora do ar");
        //}

        System.out.println("Notificação enviada para o usuário");
    }
}
