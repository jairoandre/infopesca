/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "contas")
public class Conta implements Serializable {

    @Id
    @Column(name = "CONT_ID")
    private Long id;
    @Column(name = "CONT_DT_COMPETENCIA")
    @Temporal(TemporalType.DATE)
    private Date competencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conta")
    private List<Viagem> viagens;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Date competencia) {
        this.competencia = competencia;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }
    @Transient
    private BigDecimal totalDespesasViagem = BigDecimal.ZERO;
    @Transient
    private BigDecimal totalVendas = BigDecimal.ZERO;
    @Transient
    private Map<Funcionario, BigDecimal> consolidadoPartes;
    @Transient
    private Map<Funcionario, BigDecimal> consolidadoVales;

    public List<Funcionario> getFuncionarios() {
        if (consolidadoPartes != null) {
            return new ArrayList(consolidadoPartes.keySet());
        } else {
            return null;
        }
    }

    public Map<Funcionario, BigDecimal> getConsolidadoPartes() {
        return consolidadoPartes;
    }

    public void setConsolidadoPartes(Map<Funcionario, BigDecimal> consolidadoPartes) {
        this.consolidadoPartes = consolidadoPartes;
    }

    public Map<Funcionario, BigDecimal> getConsolidadoVales() {
        return consolidadoVales;
    }

    public void setConsolidadoVales(Map<Funcionario, BigDecimal> consolidadoVales) {
        this.consolidadoVales = consolidadoVales;
    }

    public BigDecimal getTotalDespesasViagem() {
        return totalDespesasViagem;
    }

    public void setTotalDespesasViagem(BigDecimal totalDespesasViagem) {
        this.totalDespesasViagem = totalDespesasViagem;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }

    public void consolidar() {
        consolidadoPartes = new TreeMap<Funcionario, BigDecimal>();
        consolidadoVales = new TreeMap<Funcionario, BigDecimal>();
        for (Viagem viagem : viagens) {
            totalVendas = totalVendas.add(viagem.getTotalVendas());
            totalDespesasViagem = totalDespesasViagem.add(viagem.getDespesasViagem());
            for (Tripulante tripulante : viagem.getTripulantes()) {
                BigDecimal parte = tripulante.getValorParte();
                BigDecimal somatorio = consolidadoPartes.get(tripulante.getFuncionario());
                if (somatorio == null) {
                    somatorio = BigDecimal.ZERO;
                }
                somatorio = somatorio.add(parte);
                consolidadoPartes.put(tripulante.getFuncionario(), somatorio);
            }
        }

    }
}
