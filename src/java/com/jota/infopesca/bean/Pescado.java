/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "pescados")
@NamedQueries({
    @NamedQuery(name = "Pescado.findAll", query = "SELECT p FROM Pescado p"),
    @NamedQuery(name = "Pescado.findById", query = "SELECT p FROM Pescado p WHERE p.id = :id")})
public class Pescado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_VL_PESO")
    @GridConfig(label="Peso", required=true)
    private BigDecimal peso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_VL_PRECO")
    @GridConfig(label="Preço/Kg",required=true,currency=true)
    private BigDecimal preco;
    @JoinColumn(name = "PEIX_ID", referencedColumnName = "PEIX_ID")
    @ManyToOne(optional = false)
    private Peixe peixe;
    @JoinColumn(name = "VEND_ID", referencedColumnName = "VEND_ID")
    @ManyToOne(optional = false)
    private Venda venda;

    public Pescado() {
    }

    public Pescado(Long id) {
        this.id = id;
    }

    public Pescado(Long id, BigDecimal peso, BigDecimal preco) {
        this.id = id;
        this.peso = peso;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Peixe getPeixe() {
        return peixe;
    }

    public void setPeixe(Peixe peixe) {
        this.peixe = peixe;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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
        if (!(object instanceof Pescado)) {
            return false;
        }
        Pescado other = (Pescado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Pescado[ id=" + id + " ]";
    }
    
}
