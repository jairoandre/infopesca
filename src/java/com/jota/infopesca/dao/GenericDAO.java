/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

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

    private String getter(String field) {
        StringBuilder getter = new StringBuilder();
        getter.append("get");
        getter.append(field.substring(0, 1).toUpperCase());
        getter.append(field.substring(1));
        return getter.toString();
    }

    public <T> List<T> listByProperties(Class<T> entityClass, T entity, String[] fields, String[] operators) throws Exception {
        List retorno = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("select o from ");
            builder.append(entityClass.getSimpleName());
            builder.append("  o  where ");
            Map<String, Object> toEvaluate = new HashMap<String, Object>();
            for (int i = 0; i < fields.length; i++) {
                Method m = entityClass.getDeclaredMethod(getter(fields[i]));
                Object value = m.invoke(m, entity);
                if (value != null) {
                    builder.append("o.");
                    builder.append(fields[i]);
                    builder.append(" ");
                    builder.append(operators[i]);
                    builder.append(" :");
                    builder.append(fields[i]);
                    toEvaluate.put(fields[i], value);
                } else {
                    builder.append(fields[i]);
                    builder.append(" is null ");
                }
            }
            Query query = em.createQuery(builder.toString());
            for (String propertyName : toEvaluate.keySet()) {
                query.setParameter(propertyName, toEvaluate.get(propertyName));
            }
            retorno = query.getResultList();
        } catch (Exception ex) {
            throw ex;
        }
        return retorno;
    }
}
