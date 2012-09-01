/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Despesa;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.enums.TipoDespesa;
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
    
    public String novaDespesa(){
        this.setInstance(new Despesa());
        return "despesa";
    }

    public void associarViagem(Viagem viagem) {
        getDespesa().setViagem(viagem);
    }

    public void onChangeTipo() {
        Despesa despesa = getDespesa();
        if (TipoDespesa.OUTRAS.equals(despesa.getTipo())) {
            despesa.setViagem(null);
        }
    }

    public boolean isShowAssociarBtn() {
        Despesa despesa = getDespesa();
        return despesa.getViagem() == null && !TipoDespesa.OUTRAS.equals(despesa.getTipo());
    }
}
