/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André
 */
@Entity
@Table(name = "viagens")
@NamedQueries({
    @NamedQuery(name = "Viagem.findAll", query = "SELECT v FROM Viagem v"),
    @NamedQuery(name = "Viagem.findById", query = "SELECT v FROM Viagem v WHERE v.id = :id"),
    @NamedQuery(name = "Viagem.findByInicio", query = "SELECT v FROM Viagem v WHERE v.inicio = :inicio"),
    @NamedQuery(name = "Viagem.findByFim", query = "SELECT v FROM Viagem v WHERE v.fim = :fim")})
public class Viagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIAG_DT_INICIO")
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIAG_DT_FIM")
    @Temporal(TemporalType.DATE)
    private Date fim;
    @JoinColumn(name = "EMBC_ID", referencedColumnName = "EMBC_ID")
    @ManyToOne(optional = false)
    private Embarcacao embarcacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "viagem")
    private Collection<Tripulante> tripulantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "viagem")
    private Collection<Despesa> despesas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "viagem")
    private Collection<Venda> vendas;
    @Column(name = "VIAG_IN_FECHADA")
    private Boolean fechada;

    public Viagem() {
    }

    public Viagem(Long id) {
        this.id = id;
    }

    public Viagem(Long id, Date inicio, Date fim) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Embarcacao getEmbarcacao() {
        return embarcacao;
    }

    public void setEmbarcacao(Embarcacao embarcacao) {
        this.embarcacao = embarcacao;
    }

    public Collection<Tripulante> getTripulantes() {
        return tripulantes;
    }

    public void setTripulantes(Collection<Tripulante> tripulantes) {
        this.tripulantes = tripulantes;
    }

    public Collection<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(Collection<Despesa> despesas) {
        this.despesas = despesas;
    }

    public Collection<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Collection<Venda> vendas) {
        this.vendas = vendas;
    }

    public Boolean getFechada() {
        return fechada;
    }

    public void setFechada(Boolean fechada) {
        this.fechada = fechada;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Viagem[ id=" + id + " ]";
    }

    public BigDecimal getDespesasTotais() {
        BigDecimal total = BigDecimal.ZERO;
        if (despesas != null) {
            for (Despesa despesa : despesas) {
                total = total.add(despesa.getCusto());
            }
        }
        return total;
    }
}
