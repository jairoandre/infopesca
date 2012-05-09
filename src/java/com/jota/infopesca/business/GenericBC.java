/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.business;

import com.jota.infopesca.dao.GenericDAO;
import com.jota.infopesca.util.QueryUtil;
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

    public List<T> listAll() throws Exception {
        List<T> list = dao.list(new QueryUtil(clazz.newInstance()));
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

    public List<T> listByProperties(QueryUtil<T> queryUtil) throws Exception {
        return dao.list(queryUtil);
    }
}
