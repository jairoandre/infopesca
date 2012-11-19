/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Embarcacao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class EmbarcacaoMB extends HardGridControl<Embarcacao> {
    
    public EmbarcacaoMB(){
        super(Embarcacao.class);
    }

}
