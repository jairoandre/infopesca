/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.enums;

import java.math.BigDecimal;

/**
 *
 * @author André
 */
public enum TipoTripulante {

    MESTRE(10d, "Mestre"), CONTRA_MESTRE(5d, "Contra-mestre"), MOTORISTA(5d, "Motorista"), SEGUNDO_MOTORISTA(3.5d, "Segundo Motorista"), GELADOR(3d, "Gelador"), SEGUNDO_GELADOR(2d, "Segundo Gelador"), CONVES(1.25d, "Convés");
    private double partes;
    private String label;

    private TipoTripulante(double partes, String label) {
        this.partes = partes;
        this.label = label;
    }

    public BigDecimal getPartes() {
        return new BigDecimal(partes);
    }

    public String getLabel() {
        return label;
    }
}
