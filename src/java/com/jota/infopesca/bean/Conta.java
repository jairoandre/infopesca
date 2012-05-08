/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
}
