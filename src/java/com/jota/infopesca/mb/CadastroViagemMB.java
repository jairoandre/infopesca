/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Embarcacao;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.business.GenericBC;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author André
 */
@ManagedBean
@SessionScoped
public class CadastroViagemMB {

    private List<Embarcacao> embarcacoes;
    
    private Viagem viagem;
    
    private GenericBC bcEmbc = new GenericBC<Embarcacao>(Embarcacao.class);
    
    private Boolean showComporTripulacao;

    public CadastroViagemMB() {
        viagem = new Viagem();
        viagem.setInicio(new Date());
    }
    
    private void updateListaEmbarcacao(){
        try{
            embarcacoes = bcEmbc.getList();
        }catch(Exception e){
            //TODO: tratar
        }
    }

    public List<Embarcacao> getEmbarcacoes() {
        updateListaEmbarcacao();
        return embarcacoes;
    }

    public void setEmbarcacoes(List<Embarcacao> embarcacoes) {
        this.embarcacoes = embarcacoes;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }
    
    public void embarcacaoChanged(){
        if(viagem.getEmbarcacao() != null){
            showComporTripulacao = true;
        }else{
            showComporTripulacao = false;
        }
    }
    
}
