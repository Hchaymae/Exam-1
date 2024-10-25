package org.example.exam.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.example.exam.entity.DVD;

import java.util.List;

@Stateless
public class DVDService {
    @PersistenceContext
    private EntityManager em;

    public void addDVD(DVD dvd) {
        em.persist(dvd);
    }

    public List<DVD> getAllDVDs() {
        return em.createQuery("SELECT d FROM DVD d", DVD.class).getResultList();
    }
}
