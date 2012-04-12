/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author 08404235783
 */
public class SoftGridControl<T> extends GridControl {
    
    public SoftGridControl(Class<T> clazz, Collection<T> list){
        super(clazz);
        setList((List)list);
        updateList();
    }

    @Override
    protected void add(Object obj) {
        getList().add((T)obj);        
    }

    @Override
    protected void alter(Object obj) {
        // Nada
    }

    @Override
    protected void remove(Object obj) {
        getList().remove((T)obj);
    }

    @Override
    protected List refresh() {
        return getList();
    }
    
    
    
}
