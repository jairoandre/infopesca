/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.enums.TipoDespesa;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André
 */
@Entity
@Table(name = "despesas")
@NamedQueries({
    @NamedQuery(name = "Despesa.findAll", query = "SELECT d FROM Despesa d"),
    @NamedQuery(name = "Despesa.findById", query = "SELECT d FROM Despesa d WHERE d.id = :id"),
    @NamedQuery(name = "Despesa.findByTipo", query = "SELECT d FROM Despesa d WHERE d.tipo = :tipo"),
    @NamedQuery(name = "Despesa.findByCusto", query = "SELECT d FROM Despesa d WHERE d.custo = :custo"),
    @NamedQuery(name = "Despesa.findByQuitada", query = "SELECT d FROM Despesa d WHERE d.quitada = :quitada")})
public class Despesa extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_CO_TIPO")
    @Enumerated(EnumType.ORDINAL)
    @GridConfig(enumerated = true, label = "Tipo", required = true)
    private TipoDespesa tipo;
    @Column(name = "DESP_TX_DESCRICAO")
    @GridConfig(label="Descrição")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_VL_CUSTO")
    @GridConfig(currency = true, label = "Custo", required = true)
    private BigDecimal custo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_BL_QUITADA")
    private Boolean quitada;
    @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
    @ManyToOne(optional = true)
    private Viagem viagem;

    public Despesa() {
        this.tipo = TipoDespesa.VIAGEM;
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

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
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
        setViagem((Viagem)parent);
    }
    
    
}
