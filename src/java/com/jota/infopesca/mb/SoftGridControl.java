/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.GridBean;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author 08404235783
 */
public class SoftGridControl<T> extends GridControl {
    
    private Object parent;
    
    public SoftGridControl(Class<T> clazz, Collection<T> list, Object parent){
        super(clazz);
        setList((List)list);
        updateList();
        this.parent = parent;
    }

    @Override
    protected void add(Object obj) {
        ((GridBean)obj).setParent(parent);
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
