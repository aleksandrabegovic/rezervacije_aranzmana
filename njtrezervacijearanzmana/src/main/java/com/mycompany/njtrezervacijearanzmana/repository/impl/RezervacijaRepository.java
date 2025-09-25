package com.mycompany.njtrezervacijearanzmana.repository.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.Rezervacija;
import com.mycompany.njtrezervacijearanzmana.repository.MyAppRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class RezervacijaRepository implements MyAppRepository<Rezervacija, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Rezervacija> findAll() {
        // učitaj i stavke (JOIN FETCH može, ali koristimo EntityGraph da izbegnemo duplikate)
        EntityGraph<?> eg = em.getEntityGraph("Rezervacija.stavke.graph");
        return em.createQuery("SELECT r FROM Rezervacija r ORDER BY r.id DESC", Rezervacija.class)
                 .setHint("jakarta.persistence.fetchgraph", eg)
                 .getResultList();
    }

    @Override
    public Rezervacija findById(Long id) throws Exception {
        EntityGraph<?> eg = em.getEntityGraph("Rezervacija.stavke.graph");
        Rezervacija r = em.createQuery(
                "SELECT r FROM Rezervacija r WHERE r.id = :id", Rezervacija.class)
            .setParameter("id", id)
            .setHint("jakarta.persistence.fetchgraph", eg)
            .getResultStream().findFirst().orElse(null);
        if (r == null) throw new Exception("Rezervacija nije pronađena!");
        return r;
    }

    @Override
    @Transactional
    public void save(Rezervacija entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Rezervacija r = em.find(Rezervacija.class, id);
        if (r != null) em.remove(r);
    }
}
