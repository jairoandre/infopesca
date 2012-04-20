/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.business;

import com.jota.infopesca.dao.GenericDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public class GenericBC<T> implements Serializable {

    private GenericDAO dao;
    private Class<T> clazz;

    public GenericBC(Class<T> clazz) {
        this.clazz = clazz;
        this.dao = new GenericDAO<T>();
    }

    public List<T> getList() throws Exception {
        List<T> list = dao.listAll(clazz);
        return list;
    }

    public T persist(T obj) throws Exception {
        dao.create(obj);
        return obj;
    }

    public void remove(T obj) throws Exception {
        dao.delete(obj);
    }

    public T update(T obj) throws Exception {
        dao.update(obj);
        return obj;
    }

    public List<T> listByProperties(Class<T> entityClass, T entity, String[] fields, String[] operators) throws Exception {
        return dao.listByProperties(entityClass, entity, fields, operators);
    }
}
