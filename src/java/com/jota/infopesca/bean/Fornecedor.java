/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jairo
 */
@Entity
@Table(name = "fornecedores")
public class Fornecedor extends GridBean {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORNEC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORNEC_NM")
    @GridConfig(label = "Nome", editable = true, required = true, size = 50)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FORNEC_CPFCNPJ")
    @GridConfig(label = "CPF/CNPJ", editable = true, required = true, mask="99.999.999/9999-99")
    private String cpfCnpj;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Fornecedor[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        return nome;
    }
    
}
