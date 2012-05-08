/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.persistence.*;
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
public class Pescado extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_VL_PESO")
    @GridConfig(label = "Peso(Kg)", required = true, weight = true)
    private BigDecimal peso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PESC_VL_PRECO")
    @GridConfig(label = "Preço/Kg", required = true, currency = true)
    private BigDecimal preco;
    @Column(name = "PESC_VL_QUANTIDADE")
    @GridConfig(label = "Peças", number = true)
    private Integer quantidade;
    @JoinColumn(name = "PEIX_ID", referencedColumnName = "PEIX_ID")
    @ManyToOne(optional = false)
    @GridConfig(label = "Peixe", listed = true)
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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
    public String toString() {
        return "com.jota.infopesca.bean.Pescado[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        StringBuilder str = new StringBuilder();
        str.append(peixe.getNome());
        str.append(" - ");
        DecimalFormat df = new DecimalFormat("#,###.000");
        str.append(df.format(peso));
        str.append(" Kg");
        if (quantidade != null) {
            str.append(" - ");
            str.append(quantidade);
            str.append(" peças");
        }
        return str.toString();
    }

    @Override
    public void setParent(Object parent) {
        venda = (Venda) parent;
    }
}
