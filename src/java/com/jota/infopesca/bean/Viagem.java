/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
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
public class Viagem extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "VIAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "VIAG_TX_IDENTIFICADOR")
    @GridConfig(label = "Identificador")
    private String identificador;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "viagem")
    private Collection<Venda> vendas;
    @Column(name = "VIAG_IN_FECHADA")
    private Boolean fechada;
    @JoinColumn(name = "CONT_ID", referencedColumnName = "CONT_ID")
    @ManyToOne(optional = true)
    private Conta conta;

    public Viagem() {
        zerar();
    }

    public Viagem(Long id) {
        this.id = id;
    }

    public Viagem(Long id, Date inicio, Date fim) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
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

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Viagem[ id=" + id + " ]";
    }
    // Lógica fechamento conta
    @Transient
    private BigDecimal totalVendas;
    @Transient
    private BigDecimal totalManutencao;
    @Transient
    private BigDecimal subTotalPosVenda;
    @Transient
    private BigDecimal subTotalPosManutencao;
    @Transient
    private BigDecimal despesasVenda;
    @Transient
    private BigDecimal despesasViagem;
    @Transient
    private BigDecimal subTotalViagem;
    @Transient
    private BigDecimal quantidadePartes;
    @Transient
    private BigDecimal metadeViagem;
    @Transient
    private BigDecimal valorParte;
    @Transient
    private static final BigDecimal TAXA_MANUTENCAO = new BigDecimal("0.2");

    private void zerar() {
        totalVendas = BigDecimal.ZERO;
        totalManutencao = BigDecimal.ZERO;
        subTotalPosVenda = BigDecimal.ZERO;
        subTotalPosManutencao = BigDecimal.ZERO;
        despesasVenda = BigDecimal.ZERO;
        despesasViagem = BigDecimal.ZERO;
        subTotalViagem = BigDecimal.ZERO;
        quantidadePartes = BigDecimal.ZERO;
        metadeViagem = BigDecimal.ZERO;
        valorParte = BigDecimal.ZERO;
    }

    public void fecharConta() {
        zerar();

        for (Venda venda : vendas) {
            totalVendas = totalVendas.add(venda.getTotal());
        }
        for (Despesa despesa : despesas) {
            switch (despesa.getTipo()) {
                case VIAGEM:
                    despesasViagem = despesasViagem.add(despesa.getCusto());
                    break;
                default:
                    despesasVenda = despesasVenda.add(despesa.getCusto());
            }
        }
        subTotalPosVenda = totalVendas.subtract(despesasVenda);
        totalManutencao = subTotalPosVenda.multiply(TAXA_MANUTENCAO);
        subTotalPosManutencao = subTotalPosVenda.subtract(totalManutencao);
        subTotalViagem = subTotalPosManutencao.subtract(despesasViagem);
        metadeViagem = subTotalViagem.divide(new BigDecimal("2.0"), metadeViagem.scale());
        //Cálculo das partes de cada tripulante
        for (Tripulante tripulante : tripulantes) {
            quantidadePartes = quantidadePartes.add(tripulante.getFuncao().getPartes());
        }

        if (!quantidadePartes.equals(BigDecimal.ZERO)) {
            valorParte = metadeViagem.divide(quantidadePartes, metadeViagem.scale());
        }
        for (Tripulante tripulante : tripulantes) {
            tripulante.setValorParte(tripulante.getFuncao().getPartes().multiply(valorParte));
        }
    }

    public boolean isEmpenhada() {
        return subTotalViagem.compareTo(despesasViagem) < 0;
    }

    public BigDecimal getEmpenho() {
        return despesasViagem.subtract(subTotalPosManutencao);
    }

    public BigDecimal getDespesasVenda() {
        return despesasVenda;
    }

    public void setDespesasVenda(BigDecimal despesasVenda) {
        this.despesasVenda = despesasVenda;
    }

    public BigDecimal getDespesasViagem() {
        return despesasViagem;
    }

    public void setDespesasViagem(BigDecimal despesasViagem) {
        this.despesasViagem = despesasViagem;
    }

    public BigDecimal getMetadeViagem() {
        return metadeViagem;
    }

    public void setMetadeViagem(BigDecimal metadeViagem) {
        this.metadeViagem = metadeViagem;
    }

    public BigDecimal getQuantidadePartes() {
        return quantidadePartes;
    }

    public void setQuantidadePartes(BigDecimal quantidadePartes) {
        this.quantidadePartes = quantidadePartes;
    }

    public BigDecimal getSubTotalPosManutencao() {
        return subTotalPosManutencao;
    }

    public void setSubTotalPosManutencao(BigDecimal subTotalPosManutencao) {
        this.subTotalPosManutencao = subTotalPosManutencao;
    }

    public BigDecimal getSubTotalPosVenda() {
        return subTotalPosVenda;
    }

    public void setSubTotalPosVenda(BigDecimal subTotalPosVenda) {
        this.subTotalPosVenda = subTotalPosVenda;
    }

    public BigDecimal getSubTotalViagem() {
        return subTotalViagem;
    }

    public void setSubTotalViagem(BigDecimal subTotalViagem) {
        this.subTotalViagem = subTotalViagem;
    }

    public BigDecimal getTotalManutencao() {
        return totalManutencao;
    }

    public void setTotalManutencao(BigDecimal totalManutencao) {
        this.totalManutencao = totalManutencao;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }

    public BigDecimal getValorParte() {
        return valorParte;
    }

    public void setValorParte(BigDecimal valorParte) {
        this.valorParte = valorParte;
    }

    @Override
    public String getOutputTextLabel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
