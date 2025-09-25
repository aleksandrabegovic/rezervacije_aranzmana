package com.mycompany.njtrezervacijearanzmana.repository.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.Zaposleni;
import com.mycompany.njtrezervacijearanzmana.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ZaposleniRepository implements MyAppRepository<Zaposleni, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Zaposleni> findAll() {
        return em.createQuery("SELECT z FROM Zaposleni z ORDER BY z.prezime, z.ime", Zaposleni.class)
                 .getResultList();
    }

    @Override
    public Zaposleni findById(Long id) throws Exception {
        Zaposleni z = em.find(Zaposleni.class, id);
        if (z == null) throw new Exception("Zaposleni nije pronađen!");
        return z;
    }

    @Override
    @Transactional
    public void save(Zaposleni entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Zaposleni z = em.find(Zaposleni.class, id);
        if (z != null) em.remove(z);
    }

    public boolean existsByKorisnickoImeIgnoreCase(String username) {
        Long cnt = em.createQuery(
            "SELECT COUNT(z) FROM Zaposleni z WHERE LOWER(z.korisnickoIme)=LOWER(:u)", Long.class)
            .setParameter("u", username)
            .getSingleResult();
        return cnt != null && cnt > 0;
    }

    public Zaposleni findByKorisnickoIme(String username) {
        return em.createQuery(
                "SELECT z FROM Zaposleni z WHERE LOWER(z.korisnickoIme)=LOWER(:u)", Zaposleni.class)
            .setParameter("u", username)
            .getResultStream().findFirst().orElse(null);
    }
}
