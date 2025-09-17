package com.example.demo.service;

import com.example.demo.model.Log;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.impl.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    //Propagation.REQUIRED => Zaten bir transection varsa onu kullanır, yoksa yeni bir tane başlatır.
    //Propagation.REQUIRES_NEW => Loglama, bildirim gönderme gibi işlemler ana işlemden bağımsız olmalıysa bu şekilde mutlaka yeni bir transection başlatılır.
    //Propagation.SUPPORTS => Mevcut bir transection varsa kullanır, yoksa da başlatmaz.Sadece okuma işlemi, transection şart değil.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void logUserCreation(String name) {
        Log log =new Log();
        log.setMessage(name + " isimli kullanıcı başarıyla oluşturuldu.");
        logRepository.save(log);

    }
}
