/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Despesa;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 08404235783
 */
@ManagedBean
@SessionScoped
public class DespesaMB extends HardGridControl<Despesa> {
    
    public DespesaMB() {
        super(Despesa.class);
    }

    public Despesa getDespesa() {
        if (getInstance() != null) {
            return (Despesa) getInstance();
        } else {
            return null;
        }
    }
    
}
