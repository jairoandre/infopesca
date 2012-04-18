/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author André
 */
@Entity
@Table(name = "peixes")
@NamedQueries({
    @NamedQuery(name = "Peixe.findAll", query = "SELECT p FROM Peixe p"),
    @NamedQuery(name = "Peixe.findById", query = "SELECT p FROM Peixe p WHERE p.id = :id"),
    @NamedQuery(name = "Peixe.findByNome", query = "SELECT p FROM Peixe p WHERE p.nome = :nome")})
public class Peixe extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEIX_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PEIX_NM")
    @GridConfig(label = "Nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "peixe", fetch = FetchType.LAZY)
    private Collection<Pescado> pescados;

    public Peixe() {
    }

    public Peixe(Long id) {
        this.id = id;
    }

    public Peixe(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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

    public Collection<Pescado> getPescados() {
        return pescados;
    }

    public void setPescados(Collection<Pescado> pescados) {
        this.pescados = pescados;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Peixe[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        return nome;
    }
    
    
}
