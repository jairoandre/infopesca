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
import javax.validation.constraints.Size;

/**
 *
 * @author André
 */
@Entity
@Table(name = "funcionarios")
public class Funcionario extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FUNC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FUNC_NM")
    @GridConfig(label = "Nome", editable = true, required = true)
    private String nome;
    @Column(name = "FUNC_DT_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    @GridConfig(date = true, required = true, label = "Data de Nascimento")
    private Date dataNascimento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FUNC_CO_CTPS")
    @GridConfig(label = "CTPS", editable = true, required = true)
    private String ctps;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "FUNC_CO_CI")
    @GridConfig(label = "CI", editable = true, required = true)
    private String ci;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FUNC_TX_ENDERECO")
    @GridConfig(label = "Endereço", editable = true, required = true, size = 100)
    private String endereco;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FUNC_TX_COMPLEMENTO")
    @GridConfig(label = "Complemento", editable = true, required = true, size = 4)
    private String compĺemento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FUNC_TX_BAIRRO")
    @GridConfig(label = "Bairro", editable = true)
    private String bairro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FUNC_TX_CIDADE")
    @GridConfig(label = "Cidade", editable = true)
    private String cidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FUNC_TX_ESTADO")
    @GridConfig(label = "Estado", editable = true)
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FUNC_TX_CEP")
    @GridConfig(label = "Estado", editable = true, size= 9)
    private String cep;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "FUNC_CO_TEL")
    @GridConfig(label = "Telefone", editable = true, required = true, mask = "(99)9999-9999")
    private String telefone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "FUNC_CO_CEL")
    @GridConfig(label = "Celular", editable = true, required = true, mask = "(99)9999-9999")
    private String celular;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FUNC_VL_SALARIO")
    @GridConfig(label = "Salário", editable = true, required = true, currency = true)
    private BigDecimal salario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private Collection<Tripulante> tripulantes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private Collection<Despesa> despesas;

    public Funcionario() {
    }

    public Funcionario(Long id) {
        this.id = id;
    }

    public Funcionario(Long id, String nome, String ctps, String ci, String endereco, String telefone, String celular, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.ctps = ctps;
        this.ci = ci;
        this.endereco = endereco;
        this.telefone = telefone;
        this.celular = celular;
        this.salario = salario;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCompĺemento() {
        return compĺemento;
    }

    public void setCompĺemento(String compĺemento) {
        this.compĺemento = compĺemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Funcionario[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        return nome;
    }
}
