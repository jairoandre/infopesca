/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.*;
import com.jota.infopesca.business.GenericBC;
import com.jota.infopesca.business.ViagemBC;
import com.jota.infopesca.enums.TipoOperacao;
import com.jota.infopesca.util.FacesUtil;
import com.jota.infopesca.util.QueryUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;

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
    private Viagem viagemPesquisa;
    private GenericBC bcEmbc = new GenericBC<Embarcacao>(Embarcacao.class);
    private SoftGridControl<Tripulante> softGridTripulante;
    private SoftGridControl<Despesa> softGridDespesa;
    private SoftGridControl<Venda> softGridVenda;
    private List<Viagem> viagens;
    private Boolean alterando = true;
    private Viagem[] viagensSelecionadas;

    public ViagemMB() {
        viagemPesquisa = new Viagem();
        updateListaEmbarcacao();
    }

    private void updateListaEmbarcacao() {
        try {
            embarcacoes = bcEmbc.listAll();
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

    public Viagem getViagemPesquisa() {
        return viagemPesquisa;
    }

    public void setViagemPesquisa(Viagem viagemPesquisa) {
        this.viagemPesquisa = viagemPesquisa;
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

    public Boolean getAlterando() {
        return alterando;
    }

    public void setAlterando(Boolean alterando) {
        this.alterando = alterando;
    }

    public Viagem[] getViagensSelecionadas() {
        return viagensSelecionadas;
    }

    public void setViagensSelecionadas(Viagem[] viagensSelecionadas) {
        this.viagensSelecionadas = viagensSelecionadas;
    }

    public String cadastrarViagem() {
        if (viagem.getTripulantes().isEmpty()) {
            FacesUtil.addError("Viagem sem tripulantes!");
        } else {
            try {
                bc.persist(viagem);
                return "pesquisaViagem";
            } catch (Exception e) {
                System.out.println(e);
                FacesUtil.addError("Erro no cadastro!");
            }
        }
        return null;
    }

    public String preIncluirViagem() {
        this.alterando = false;
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
                    }
                }
                return true;
            }
        };

        viagem.setDespesas(new ArrayList<Despesa>());
        softGridDespesa = new SoftGridControl<Despesa>(Despesa.class, viagem.getDespesas(), viagem);
        viagem.setVendas(new ArrayList<Venda>());
        softGridVenda = new SoftGridControl<Venda>(Venda.class, viagem.getVendas(), viagem);

        return "manterViagem";
    }

    public void preAlterarViagem(Viagem viag) {
        viagem = viag;
        this.alterando = true;
        softGridTripulante = new SoftGridControl<Tripulante>(Tripulante.class, viagem.getTripulantes(), viagem) {

            @Override
            @SuppressWarnings("empty-statement")
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
        softGridDespesa = new SoftGridControl<Despesa>(Despesa.class, viagem.getDespesas(), viagem);
        softGridVenda = new SoftGridControl<Venda>(Venda.class, viagem.getVendas(), viagem);
    }
    public static final String[] CAMPOS_PESQUISA = {"embarcacao", "inicio", "fim"};
    public static final String[] OPERADORES = {"=", ">=", "<="};

    public void pesquisarViagens() {
        QueryUtil<Viagem> queryUtil = new QueryUtil<Viagem>(viagemPesquisa);
        queryUtil.addCriteria("embarcacao", TipoOperacao.EQ);
        queryUtil.addCriteria("inicio", TipoOperacao.GE);
        queryUtil.addCriteria("fim", TipoOperacao.LE);
        try {
            viagens = bc.listByProperties(queryUtil);
            viagemPesquisa = new Viagem();
        } catch (Exception e) {
            System.out.println(e);
            FacesUtil.addError("Erro na consulta.");
        }
    }

    public void fecharConta() {
    }

    public void onTabChange(TabChangeEvent evt) {
        Tab tab = evt.getTab();
        if (tab.getClientId().contains("balanco")) {
            viagem.fecharConta();
        }
    }

    public boolean isExibirBtnFecharConta() {
        return viagem.getId() != null && !viagem.getFechada();
    }

    public void carregarTripulacaoRecente() {
        ViagemBC viagemBC = new ViagemBC();
        try {
            if (viagem.getEmbarcacao() == null) {
                FacesUtil.addError("Informe a embarcação.");
            } else {
                List<Tripulante> tripulantes = viagemBC.carregarTripulacaoRecente(viagem);
                if (tripulantes.isEmpty()) {
                    FacesUtil.addWarn("Não há tripulação recente.");
                } else {
                    viagem.setTripulantes(tripulantes);
                    softGridTripulante.setList(tripulantes);
                }
            }

        } catch (Exception e) {
            FacesUtil.addError("Erro na obtenção da lista de tripulantes.");
        }
    }
}
