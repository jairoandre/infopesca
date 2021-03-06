/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import com.jota.infopesca.enums.TipoTripulante;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André
 */
@Entity
@Table(name = "tripulantes")
@NamedQueries({
    @NamedQuery(name = "Tripulante.findAll", query = "SELECT t FROM Tripulante t"),
    @NamedQuery(name = "Tripulante.findById", query = "SELECT t FROM Tripulante t WHERE t.id = :id"),
    @NamedQuery(name = "Tripulante.findByFuncao", query = "SELECT t FROM Tripulante t WHERE t.funcao = :funcao")})
public class Tripulante extends GridBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRIP_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRIPNT_CO_FUNCAO")
    @Enumerated(EnumType.ORDINAL)
    @GridConfig(editable=true,label="Posto",enumerated=true, required=true)
    private TipoTripulante funcao;
    @JoinColumn(name = "FUNC_ID", referencedColumnName = "FUNC_ID")
    @ManyToOne(optional = false)
    @GridConfig(editable=true,label="Funcionário",listed=true, required=true)
    private Funcionario funcionario;
    @JoinColumn(name = "VIAG_ID", referencedColumnName = "VIAG_ID")
    @ManyToOne(optional = false)
    private Viagem viagem;
    
    @Transient
    private BigDecimal valorParte = BigDecimal.ZERO;

    public Tripulante() {
    }

    public Tripulante(Long id) {
        this.id = id;
    }

    public Tripulante(Long id, TipoTripulante funcao) {
        this.id = id;
        this.funcao = funcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTripulante getFuncao() {
        return funcao;
    }

    public void setFuncao(TipoTripulante funcao) {
        this.funcao = funcao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public BigDecimal getValorParte() {
        return valorParte;
    }

    public void setValorParte(BigDecimal valorParte) {
        this.valorParte = valorParte;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Tripulante[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        return getFuncionario().getNome();
    }
    
    @Override
    public void setParent(Object parent){
        setViagem((Viagem)parent);
    }
    
}
