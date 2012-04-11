/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.business.GenericBC;
import java.util.List;

/**
 * Classe genérica para manutenção de CRUD simples.
 *
 * @author root
 */
public class HardGridControl<T> extends GridControl {

    private GenericBC<T> bc;

    public HardGridControl(Class<T> clazz) {
        super(clazz);
        this.bc = new GenericBC<T>(clazz);
        updateList();
    }

    @Override
    protected void add(Object obj) {
        try {
            bc.persist((T) obj);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    protected void alter(Object obj) {
        try {
            bc.update((T) obj);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void remove(Object obj) {
        try {
            bc.remove((T) obj);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected List<T> refresh() {
        try {
            return bc.getList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
