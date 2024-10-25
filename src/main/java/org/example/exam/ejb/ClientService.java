package org.example.exam.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
        import org.example.exam.entity.CD;
import org.example.exam.entity.Client;

import java.util.List;

@Stateless
public class ClientService {
    @PersistenceContext
    private EntityManager em;

    public List<CD> getAllCDs() {
        return em.createQuery("SELECT c FROM CD c", CD.class).getResultList();
    }

    public void borrowCD(Long cdId, Long clientId) {
        CD cd = em.find(CD.class, cdId);
        Client client = em.find(Client.class, clientId);
        if (cd != null && client != null && !cd.isBorrowed()) {
            cd.setBorrowed(true);
            cd.setClient(client);
            em.merge(cd);
        }
    }

    public void returnCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && cd.isBorrowed()) {
            cd.setBorrowed(false);
            cd.setClient(null);
            em.merge(cd);
        }
    }
}