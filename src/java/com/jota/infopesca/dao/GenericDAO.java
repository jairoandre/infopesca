/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.dao;

import com.jota.infopesca.util.QueryUtil;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author root
 */
public class GenericDAO<T> implements Serializable {

    private EntityManager em;

    public GenericDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("infopescaPU");
        em = emf.createEntityManager();
    }

    public <T> T create(T entity) throws Exception {
        Exception e = null;
        T retornoEntity = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            retornoEntity = em.merge(entity);
            t.commit();
        } catch (Exception ex) {
            if (t.isActive()) {
                em.getTransaction().rollback();
            }
            e = ex;
        }
        if (e != null) {
            throw e;
        }
        return retornoEntity;
    }

    public <T> T update(T entity) throws Exception {
        Exception e = null;
        T retornoEntity = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            retornoEntity = em.merge(entity);
            t.commit();
        } catch (Exception ex) {
            if (t.isActive()) {
                t.rollback();
            }
            e = ex;
        }
        if (e != null) {
            throw e;
        }
        return retornoEntity;
    }

    public <T> void delete(T entity) throws Exception {
        Exception e = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            entity = em.merge(entity);
            em.remove(entity);
            t.commit();
        } catch (Exception ex) {
            if (t.isActive()) {
                t.rollback();
            }
            e = ex;
        }
        if (e != null) {
            throw e;
        }
    }

    public <T> T retrieve(Class<T> entityClass, Object id) throws Exception {
        Exception e = null;
        T retorno = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            retorno = (T) em.find(entityClass, id);
            t.commit();
        } catch (Exception ex) {
            if (t.isActive()) {
                t.rollback();
            }
            e = ex;
        }
        if (e != null) {
            throw e;
        }
        return retorno;
    }

    public <T> List<T> listAll(Class<T> entityClass, String... orderbys) throws Exception {
        List retorno = null;
        try {
            String query = "select o from " + entityClass.getSimpleName() + "  o  order by ";

            for (String order : orderbys) {
                query += order + " , ";
            }
            query = query.substring(0, query.length() - 2);
            Query q = em.createQuery(query);
            retorno = q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return retorno;
    }

    public <T> List<T> listAll(Class<T> entityClass) throws Exception {
        List retorno = null;
        try {
            String query = "select o from " + entityClass.getSimpleName() + "  o ";
            Query q = em.createQuery(query);
            retorno = q.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return retorno;
    }

    public <T> List<T> listByProperties(QueryUtil<T> queryUtil) throws Exception {
        List retorno = null;
        try {

            Query query = em.createQuery(queryUtil.createQuery(em.getCriteriaBuilder()));

            retorno = query.getResultList();

        } catch (Exception ex) {
            throw ex;
        }
        return retorno;
    }
}
