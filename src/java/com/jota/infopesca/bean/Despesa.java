/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.enums.TipoDespesa;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
public class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_CO_TIPO")
    @Enumerated(EnumType.ORDINAL)
    private TipoDespesa tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_VL_CUSTO")
    private double custo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DESP_BL_QUITADA")
    private boolean quitada;
    @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
    @ManyToOne(optional = true)
    private Viagem viagem;

    public Despesa() {
    }

    public Despesa(Long id) {
        this.id = id;
    }

    public Despesa(Long id, TipoDespesa tipo, double custo, boolean quitada) {
        this.id = id;
        this.tipo = tipo;
        this.custo = custo;
        this.quitada = quitada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDespesa getTipo() {
        return tipo;
    }

    public void setTipo(TipoDespesa tipo) {
        this.tipo = tipo;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public boolean getQuitada() {
        return quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Despesa)) {
            return false;
        }
        Despesa other = (Despesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Despesa[ id=" + id + " ]";
    }
    
}
