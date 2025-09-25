/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.njtrezervacijearanzmana.repository.impl;

import com.mycompany.njtrezervacijearanzmana.entity.impl.TipAranzmana; 
import com.mycompany.njtrezervacijearanzmana.repository.MyAppRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TipAranzmanaRepository implements MyAppRepository<TipAranzmana, Long> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TipAranzmana> findAll() {
        return em.createQuery("SELECT t FROM TipAranzmana t ORDER BY t.naziv", TipAranzmana.class)
                 .getResultList();
    }

    @Override
    public TipAranzmana findById(Long id) throws Exception {
        TipAranzmana t = em.find(TipAranzmana.class, id);
        if (t == null) throw new Exception("Tip aranžmana nije pronađen!");
        return t;
    }

    @Override
    @Transactional
    public void save(TipAranzmana entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TipAranzmana t = em.find(TipAranzmana.class, id);
        if (t != null) em.remove(t);
    }

    public boolean existsByNazivIgnoreCase(String naziv) {
        Long cnt = em.createQuery(
                "SELECT COUNT(t) FROM TipAranzmana t WHERE LOWER(t.naziv)=LOWER(:n)", Long.class)
            .setParameter("n", naziv)
            .getSingleResult();
        return cnt != null && cnt > 0;
    }
}
