/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
public class Peixe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEIX_ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PEIX_NM")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "peixe")
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peixe)) {
            return false;
        }
        Peixe other = (Peixe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Peixe[ id=" + id + " ]";
    }
    
}
