/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.*;
import com.jota.infopesca.business.GenericBC;
import com.jota.infopesca.util.FacesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author André
 */
@ManagedBean
@SessionScoped
public class CadastroViagemMB {
    
    Logger LOG = Logger.getLogger("CadastroViagemMB");
    
    private List<Embarcacao> embarcacoes;
    
    private Viagem viagem;
    
    private GenericBC bcEmbc = new GenericBC<Embarcacao>(Embarcacao.class);
    
    private SoftGridControl<Tripulante> softGridTripulante;
    
    private SoftGridControl<Despesa> softGridDespesa;
    
    public CadastroViagemMB() {
        viagem = new Viagem();
        viagem.setInicio(new Date());
        viagem.setTripulantes(new ArrayList<Tripulante>());
        softGridTripulante = new SoftGridControl<Tripulante>(Tripulante.class, viagem.getTripulantes(), viagem){

            @Override
            protected boolean validateInclude() {
                Funcionario func = ((Tripulante)getInstance()).getFuncionario();
                for(Object trip : getList()){
                    if(func.equals(((Tripulante)trip).getFuncionario())){
                        FacesUtil.addError("Funcionário já é tripulante.");
                        return false;
                    };
                }
                return true;
            }
        };
        
        viagem.setDespesas(new ArrayList<Despesa>());
        softGridDespesa = new SoftGridControl<Despesa>(Despesa.class, viagem.getDespesas(), viagem);
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
    
    public SoftGridControl<Despesa> getSoftGridDespesa() {
        return softGridDespesa;
    }

    public void setSoftGridDespesa(SoftGridControl<Despesa> softGridDespesa) {
        this.softGridDespesa = softGridDespesa;
    }
    
    public String cadastrarViagem(){
        if(viagem.getTripulantes().isEmpty()){
            FacesUtil.addError("Viagem sem tripulantes!");
        }else{
            GenericBC<Viagem> bc = new GenericBC<Viagem>(Viagem.class);
            try{
                bc.persist(viagem);
                return "home";
            }catch(Exception ex){
                LOG.log(Level.OFF, ex.getMessage());
                FacesUtil.addError("Erro no cadastro!");
            }           
        }
        return null;
    }
    
}
