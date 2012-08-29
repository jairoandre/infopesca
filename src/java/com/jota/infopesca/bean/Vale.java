/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "vales")
public class Vale extends GridBean implements Serializable {

    @Id
    @Column(name = "VAL_ID")
    private Long id;
    @JoinColumn(name = "FUNC_ID", referencedColumnName = "FUNC_ID")
    @ManyToOne(optional = true)
    @GridConfig(editable = true, listed = true, required = true, label = "Funcionário")
    private Funcionario funcionario;
    @Column(name = "VL_DT_CRIACAO")
    @Temporal(TemporalType.DATE)
    @GridConfig(editable = true, date = true, label = "Data Referência")
    private Date criacao;
    @Column(name = "VL_DT_QUITACAO")
    @Temporal(TemporalType.DATE)
    @GridConfig(editable = true, date = true, label = "Data de Quitação")
    private Date quitacao;
    @Column(name = "VAL_VL")
    @GridConfig(editable = true, currency = true, label = "Valor")
    private BigDecimal valor;
    @Column(name = "VAL_IN_QUITADA")
    @GridConfig(editable = true, flag = true, label = "Paga")
    private Boolean quitada;

    public Vale() {
        this.quitada = false;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public Date getQuitacao() {
        return quitacao;
    }

    public void setQuitacao(Date quitacao) {
        this.quitacao = quitacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getQuitada() {
        return quitada;
    }

    public void setQuitada(Boolean quitada) {
        this.quitada = quitada;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String getOutputTextLabel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
