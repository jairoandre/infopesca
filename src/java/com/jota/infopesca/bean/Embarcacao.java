/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.pattern.GridBean;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author root
 */
@Entity
@Table(name = "embarcacoes")
@NamedQueries({
    @NamedQuery(name = "Embarcacao.findAll", query = "SELECT e FROM Embarcacao e"),
    @NamedQuery(name = "Embarcacao.findById", query = "SELECT e FROM Embarcacao e WHERE e.id = :id"),
    @NamedQuery(name = "Embarcacao.findByNome", query = "SELECT e FROM Embarcacao e WHERE e.nome = :nome")})
public class Embarcacao extends GridBean {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMBC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "EMBC_NM")
    @GridConfig(label = "Nome", required = true)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "embarcacao")
    private Collection<Viagem> viagens;

    public Embarcacao() {
    }

    public Embarcacao(Long id) {
        this.id = id;
    }

    public Embarcacao(Long id, String nome) {
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

    public Collection<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(Collection<Viagem> viagens) {
        this.viagens = viagens;
    }

    @Override
    public String toString() {
        return "com.jota.infopesca.bean.Embarcacao[ id=" + id + " ]";
    }

    @Override
    public String getOutputTextLabel() {
        return nome;
    }
}
