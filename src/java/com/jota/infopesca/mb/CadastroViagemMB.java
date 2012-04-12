/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Embarcacao;
import com.jota.infopesca.bean.Tripulante;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.business.GenericBC;
import java.util.ArrayList;
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
    
    private SoftGridControl<Tripulante> softGridTripulante;
    
    public CadastroViagemMB() {
        viagem = new Viagem();
        viagem.setInicio(new Date());
        viagem.setTripulantes(new ArrayList<Tripulante>());
        softGridTripulante = new SoftGridControl<Tripulante>(Tripulante.class, viagem.getTripulantes());
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
        }else{
        }
    }
    
    public SoftGridControl<Tripulante> getSoftGridTripulante() {
        return softGridTripulante;
    }

    public void setSoftGridTripulante(SoftGridControl<Tripulante> softGridTripulante) {
        this.softGridTripulante = softGridTripulante;
    }
    
}
