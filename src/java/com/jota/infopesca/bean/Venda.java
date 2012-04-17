/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import javax.persistence.*;
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
public class Venda extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VEND_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VEND_VL_TOTAL")
    @GridConfig(label = "Total", required = true, currency = true)
    private BigDecimal total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda")
    @GridConfig(label = "Pescado", griddable = true, softGridClass = Pescado.class)
    private Collection<Pescado> pescados;
    @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
    @ManyToOne(optional = false)
    private Viagem viagem;
    @Column(name = "VEND_IN_EXTRA")
    @GridConfig(label = "Extra", required = true, flag=true)
    private Boolean extra;

    public Venda() {
        extra = false;
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

    public Boolean getExtra() {
        return extra;
    }

    public void setExtra(Boolean extra) {
        this.extra = extra;
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

    @Override
    public String getOutputTextLabel() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "R$ " + df.format(total.doubleValue());
    }

    @Override
    public void setParent(Object parent) {
        this.viagem = (Viagem) parent;
    }
}
