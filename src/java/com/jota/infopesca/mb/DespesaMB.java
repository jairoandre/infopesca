/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Despesa;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.enums.TipoDespesa;
import com.jota.infopesca.enums.TipoOperacao;
import com.jota.infopesca.util.FacesUtil;
import com.jota.infopesca.util.QueryUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 08404235783
 */
@ManagedBean
@SessionScoped
public class DespesaMB extends HardGridControl<Despesa> {

  public Despesa despesaPesquisa;

  public DespesaMB() {
    super(Despesa.class);
    despesaPesquisa = new Despesa();
  }

  public Despesa getDespesaPesquisa() {
    return despesaPesquisa;
  }

  public void setDespesaPesquisa(Despesa despesaPesquisa) {
    this.despesaPesquisa = despesaPesquisa;
  }

  public Despesa getDespesa() {
    if (getInstance() != null) {
      return (Despesa) getInstance();
    } else {
      return null;
    }
  }

  public String novaDespesa() {
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

  public boolean isShowGroupViagem() {
    Despesa despesa = getDespesa();
    return TipoDespesa.MANUTENCAO.equals(despesa.getTipo()) || TipoDespesa.VIAGEM.equals(despesa.getTipo());
  }

  private List<Despesa> pesquisar() {
    try {
      QueryUtil<Despesa> query = new QueryUtil<Despesa>(despesaPesquisa);
      query.addCriteria("tipo", TipoOperacao.EQ);
      query.addCriteria("notaFiscal", TipoOperacao.EQ);
      return getBc().listByProperties(query);
    } catch (Exception e) {
      return null;
    }
  }

  public void filtrar() {
    List<Despesa> lista = pesquisar();
    if (lista == null) {
      FacesUtil.addWarn("Erro na pesquisa.");
    } else {
      setList(lista);
    }
  }

  @Override
  protected List<Despesa> refresh() {
    List<Despesa> lista = pesquisar();
    if (lista == null) {
      return super.refresh();
    } else {
      return lista;
    }
  }
}
