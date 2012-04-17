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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author André
 */
@ManagedBean
@SessionScoped
public class ViagemMB {

    private GenericBC<Viagem> bc = new GenericBC<Viagem>(Viagem.class);
    private List<Embarcacao> embarcacoes;
    private Viagem viagem;
    private GenericBC bcEmbc = new GenericBC<Embarcacao>(Embarcacao.class);
    private SoftGridControl<Tripulante> softGridTripulante;
    private SoftGridControl<Despesa> softGridDespesa;
    private SoftGridControl<Venda> softGridVenda;
    private List<Viagem> viagens;

    public ViagemMB() {
        refreshViagens();
    }

    private void updateListaEmbarcacao() {
        try {
            embarcacoes = bcEmbc.getList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public SoftGridControl<Venda> getSoftGridVenda() {
        return softGridVenda;
    }

    public void setSoftGridVenda(SoftGridControl<Venda> softGridVenda) {
        this.softGridVenda = softGridVenda;
    }
    
    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    private void refreshViagens() {
        try {
            viagens = bc.getList();
            List<Viagem> viagensToShow = new ArrayList<Viagem>();
            for (Viagem viag : viagens) {
                if (!viag.getFechada()) {
                    viagensToShow.add(viag);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            FacesUtil.addError("Erro na consulta!");
        }
    }

    public String cadastrarViagem() {
        if (viagem.getTripulantes().isEmpty()) {
            FacesUtil.addError("Viagem sem tripulantes!");
        } else {
            try {
                bc.persist(viagem);
                return viagensEmAberto();
            } catch (Exception e) {
                System.out.println(e);
                FacesUtil.addError("Erro no cadastro!");
            }
        }
        return null;
    }

    public String viagensEmAberto() {
        refreshViagens();
        return "viagensEmAberto";
    }

    public String preIncluirViagem() {
        viagem = new Viagem();
        viagem.setInicio(new Date());
        viagem.setFechada(false);
        viagem.setTripulantes(new ArrayList<Tripulante>());
        softGridTripulante = new SoftGridControl<Tripulante>(Tripulante.class, viagem.getTripulantes(), viagem) {

            @Override
            protected boolean validateInclude() {
                Funcionario func = ((Tripulante) getInstance()).getFuncionario();
                for (Object trip : getList()) {
                    if (func.equals(((Tripulante) trip).getFuncionario())) {
                        FacesUtil.addError("Funcionário já é tripulante.");
                        return false;
                    };
                }
                return true;
            }
        };

        viagem.setDespesas(new ArrayList<Despesa>());
        softGridDespesa = new SoftGridControl<Despesa>(Despesa.class, viagem.getDespesas(), viagem);
        viagem.setVendas(new ArrayList<Venda>());
        softGridVenda = new SoftGridControl<Venda>(Venda.class, viagem.getVendas(), viagem);

        return "cadastroViagem";
    }
}
