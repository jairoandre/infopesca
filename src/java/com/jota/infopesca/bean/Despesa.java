/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import com.jota.infopesca.enums.TipoDespesa;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André
 */
@Entity
@Table(name = "despesas")
public class Despesa extends GridBean {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "DESP_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Column(name = "DESP_CO_TIPO")
  @Enumerated(EnumType.ORDINAL)
  @GridConfig(enumerated = true, label = "Tipo", required = true, visible = false)
  private TipoDespesa tipo;
  @Column(name = "DESP_CO_NF")
  @GridConfig(label = "Nota Fiscal", size = 20)
  private String notaFiscal;
  @Basic(optional = false)
  @NotNull
  @Column(name = "DESP_VL_CUSTO")
  @GridConfig(currency = true, label = "Custo", required = true)
  private BigDecimal custo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "DESP_BL_QUITADA")
  @GridConfig(label = "Quitada", required = true, flag = true)
  private Boolean quitada;
  @Column(name = "DESP_DT_VENCIMENTO")
  @Temporal(TemporalType.DATE)
  @GridConfig(label = "Vencimento", date = true, columnVisible = false)
  private Date vencimento;
  @Column(name = "DESP_DT_PAGAMENTO")
  @Temporal(TemporalType.DATE)
  @GridConfig(label = "Data Pagamento", date = true)
  private Date pagamento;
  @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
  @ManyToOne(optional = true)
  @GridConfig(label = "Embarcação", editable = false)
  private Viagem viagem;
  @JoinColumn(name = "FUNC_ID", referencedColumnName = "FUNC_ID")
  @ManyToOne(optional = true)
  private Funcionario funcionario;
  @JoinColumn(name = "FORNEC_ID", referencedColumnName = "FORNEC_ID")
  @ManyToOne(optional = true)
  @GridConfig(label = "Fornecedor", listed = true)
  private Fornecedor fornecedor;
  @Column(name = "DESP_TX_DESCRICAO")
  @GridConfig(label = "Descrição", textArea = true)
  private String descricao;

  public Despesa() {
    this.tipo = TipoDespesa.OUTRAS;
    this.quitada = true;
  }

  public Despesa(Long id) {
    this.id = id;
  }

  public Despesa(Long id, TipoDespesa tipo, BigDecimal custo, boolean quitada) {
    this.id = id;
    this.tipo = tipo;
    this.custo = custo;
    this.quitada = quitada;
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getNotaFiscal() {
    return notaFiscal;
  }

  public void setNotaFiscal(String notaFiscal) {
    this.notaFiscal = notaFiscal;
  }

  public TipoDespesa getTipo() {
    return tipo;
  }

  public void setTipo(TipoDespesa tipo) {
    this.tipo = tipo;
  }

  public BigDecimal getCusto() {
    return custo;
  }

  public void setCusto(BigDecimal custo) {
    this.custo = custo;
  }

  public Boolean getQuitada() {
    return quitada;
  }

  public void setQuitada(Boolean quitada) {
    this.quitada = quitada;
  }

  public Date getVencimento() {
    return vencimento;
  }

  public void setVencimento(Date vencimento) {
    this.vencimento = vencimento;
  }

  public Date getPagamento() {
    return pagamento;
  }

  public void setPagamento(Date pagamento) {
    this.pagamento = pagamento;
  }

  public Viagem getViagem() {
    return viagem;
  }

  public void setViagem(Viagem viagem) {
    this.viagem = viagem;
  }

  public Funcionario getFuncionario() {
    return funcionario;
  }

  public void setFuncionario(Funcionario funcionario) {
    this.funcionario = funcionario;
  }

  public Fornecedor getFornecedor() {
    return fornecedor;
  }

  public void setFornecedor(Fornecedor fornecedor) {
    this.fornecedor = fornecedor;
  }

  @Override
  public String toString() {
    return "com.jota.infopesca.bean.Despesa[ id=" + id + " ]";
  }

  @Override
  public String getOutputTextLabel() {
    return "";
  }

  @Override
  public void setParent(Object parent) {
    if (Viagem.class.equals(parent.getClass())) {
      setViagem((Viagem) parent);

    } else if (Funcionario.class.equals(parent.getClass())) {
      setFuncionario((Funcionario) parent);
    }
  }
}
