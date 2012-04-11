/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import java.util.List;

/**
 *
 * @author 08404235783
 */
public class SoftGridControl<T> extends GridControl {
    
    private List<T> list;
    
    public SoftGridControl(Class<T> clazz, List<T> list){
        super(clazz);
        this.list = list;
    }

    @Override
    protected void add(Object obj) {
        list.add((T)obj);        
    }

    @Override
    protected void alter(Object obj) {
        // Nada
    }

    @Override
    protected void remove(Object obj) {
        list.remove((T)obj);
    }

    @Override
    protected List refresh() {
        return list;
    }
    
    
    
}
