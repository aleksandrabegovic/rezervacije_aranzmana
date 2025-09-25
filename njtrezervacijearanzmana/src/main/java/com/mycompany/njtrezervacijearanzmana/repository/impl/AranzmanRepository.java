package com.mycompany.njtrezervacijearanzmana.repository.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.Aranzman;
import com.mycompany.njtrezervacijearanzmana.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AranzmanRepository implements MyAppRepository<Aranzman, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Aranzman> findAll() {
        return em.createQuery("SELECT a FROM Aranzman a ORDER BY a.naziv", Aranzman.class)
                 .getResultList();
    }

    @Override
    public Aranzman findById(Long id) throws Exception {
        Aranzman a = em.find(Aranzman.class, id);
        if (a == null) throw new Exception("Aranžman nije pronađen!");
        return a;
    }

    @Override
    @Transactional
    public void save(Aranzman entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Aranzman a = em.find(Aranzman.class, id);
        if (a != null) em.remove(a);
    }

    public boolean existsByNazivAndDestinacijaIgnoreCase(String naziv, String destinacija) {
        Long cnt = em.createQuery(
            "SELECT COUNT(a) FROM Aranzman a " +
            "WHERE LOWER(a.naziv)=LOWER(:n) AND LOWER(a.destinacija)=LOWER(:d)", Long.class)
            .setParameter("n", naziv)
            .setParameter("d", destinacija)
            .getSingleResult();
        return cnt != null && cnt > 0;
    }
}
