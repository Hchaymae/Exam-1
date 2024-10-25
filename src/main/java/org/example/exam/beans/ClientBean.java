package org.example.exam.beans;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import org.example.exam.ejb.ClientService;
import org.example.exam.entity.CD;

import java.util.List;

@Named
@RequestScoped
public class ClientBean {
    @EJB
    private ClientService clientService;

    private Long cdId;
    private Long clientId;

    public List<CD> getAllCDs() {
        return clientService.getAllCDs();
    }

    public void borrowCD() {
        clientService.borrowCD(cdId, clientId);
    }

    public void returnCD() {
        clientService.returnCD(cdId);
    }

    public Long getCdId() {
        return cdId;
    }

    public void setCdId(Long cdId) {
        this.cdId = cdId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}

