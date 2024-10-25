package org.example.exam.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.example.exam.entity.CD;

import java.util.List;

@Stateless
public class CDService {
    @PersistenceContext
    private EntityManager em;

    public void addCD(CD cd) {
        em.persist(cd);
    }

    public void updateCD(CD cd) {
        em.merge(cd);
    }

    public void deleteCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null) {
            em.remove(cd);
        }
    }

    public List<CD> getAllCDs() {
        return em.createQuery("SELECT c FROM CD c", CD.class).getResultList();
    }

    public void borrowCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && !cd.isBorrowed()) {
            cd.setBorrowed(true);
            em.merge(cd);
        }
    }

    public List<CD> getBorrowedCDs() {
        return em.createQuery("SELECT c FROM CD c WHERE c.borrowed = true", CD.class).getResultList();
    }

    public void returnCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && cd.isBorrowed()) {
            cd.setBorrowed(false);
            em.merge(cd);
        }
    }
}