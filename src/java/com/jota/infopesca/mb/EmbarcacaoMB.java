/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Embarcacao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author root
 */
@ManagedBean
@RequestScoped
public class EmbarcacaoMB extends GridControl<Embarcacao> {
    
    public EmbarcacaoMB(){
        super(Embarcacao.class);
    }    
       
}
