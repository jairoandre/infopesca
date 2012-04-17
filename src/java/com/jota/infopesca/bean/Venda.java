/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André
 */
@Entity
@Table(name = "VENDAS")
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT c FROM Venda c"),
    @NamedQuery(name = "Venda.findById", query = "SELECT c FROM Venda c WHERE c.id = :id")})
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VEND_ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VEND_VL_TOTAL")
    private BigDecimal total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda")
    private Collection<Pescado> pescados;
    @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
    @ManyToOne(optional = false)
    private Viagem viagem;

    public Venda() {
    }

    public Venda(Long id) {
        this.id = id;
    }

    public Venda(Long id, BigDecimal total) {
        this.id = id;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Collection<Pescado> getPescados() {
        return pescados;
    }

    public void setPescados(Collection<Pescado> pescados) {
        this.pescados = pescados;
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
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Venda[ id=" + id + " ]";
    }
    
}
