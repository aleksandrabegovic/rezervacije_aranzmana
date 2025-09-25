package com.mycompany.njtrezervacijearanzmana.repository.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.Putnik;
import com.mycompany.njtrezervacijearanzmana.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PutnikRepository implements MyAppRepository<Putnik, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Putnik> findAll() {
        return em.createQuery("SELECT p FROM Putnik p ORDER BY p.prezime, p.ime", Putnik.class)
                 .getResultList();
    }

    @Override
    public Putnik findById(Long id) throws Exception {
        Putnik p = em.find(Putnik.class, id);
        if (p == null) throw new Exception("Putnik nije pronađen!");
        return p;
    }

    @Override
    @Transactional
    public void save(Putnik entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Putnik p = em.find(Putnik.class, id);
        if (p != null) em.remove(p);
    }

    public boolean existsByEmailIgnoreCase(String email) {
        Long cnt = em.createQuery(
            "SELECT COUNT(p) FROM Putnik p WHERE LOWER(p.email)=LOWER(:e)", Long.class)
            .setParameter("e", email)
            .getSingleResult();
        return cnt != null && cnt > 0;
    }
}
