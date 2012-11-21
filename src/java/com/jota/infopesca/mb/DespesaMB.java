/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Despesa;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.enums.TipoDespesa;
import com.jota.infopesca.enums.TipoOperacao;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author 08404235783
 */
@ManagedBean
@ViewScoped
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

  public String novaDespesa() {
    this.setInstance(new Despesa());
    return "despesa";
  }

  public void associarViagem(Viagem viagem) {
    getDespesa().setViagem(viagem);
  }

  public void desassociarViagem() {
    getDespesa().setViagem(null);
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

  @Override
  protected void init() {
    Despesa sample = new Despesa();
    sample.setTipo(null);
    setSample(sample);
    Map<String, TipoOperacao> searchParams = new HashMap<String, TipoOperacao>();
    searchParams.put("tipo", TipoOperacao.EQ);
    searchParams.put("notaFiscal", TipoOperacao.EQ);
    searchParams.put("fornecedor", TipoOperacao.EQ);
    setSearchParams(searchParams);
  }

  public BigDecimal getTotal() {
    BigDecimal total = BigDecimal.ZERO;
    for (Object obj : getList()) {
      Despesa desp = (Despesa) obj;
      BigDecimal custo = desp.getCusto();
      if (custo != null) {
        total = total.add(desp.getCusto());
      }
    }
    return total;
  }
}
