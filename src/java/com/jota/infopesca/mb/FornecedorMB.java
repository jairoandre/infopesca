/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Fornecedor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author root
 */
@ManagedBean
@ViewScoped
public class FornecedorMB extends HardGridControl<Fornecedor> {

    public FornecedorMB() {
        super(Fornecedor.class);
    }
}
