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

    MESTRE("6.0", "Mestre"), CONTRA_MESTRE("2.0", "Contra-mestre"), COZINHEIRO("2.0", "Cozinheiro"), MOTORISTA("4.0", "Motorista"), SEGUNDO_MOTORISTA("1.75", "Segundo Motorista"), GELADOR("2.0", "Gelador"), SEGUNDO_GELADOR("1.75", "Segundo Gelador"), CONVES("1.25", "Convés");
    private String partes;
    private String label;

    private TipoTripulante(String partes, String label) {
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
