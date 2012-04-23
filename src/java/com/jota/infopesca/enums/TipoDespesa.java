/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.enums;

/**
 *
 * @author André
 */
public enum TipoDespesa {

    VENDA("Venda"), VIAGEM("Viagem");
    private String label;

    private TipoDespesa(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
