package com.restaurante.restaurante.observer;


import com.restaurante.restaurante.models.Client;
import com.restaurante.restaurante.models.Orders;
import com.restaurante.restaurante.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientStatusObserver {

    private final ClientRepository clientRepository;


    @Autowired
    public ClientStatusObserver(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }


//    @Override
//    public void onOrderCreated(Orders order) {
//        clientRepository.checkAndUpdateClientStatus(order.getClient().getId());
//    }

}
