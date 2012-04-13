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

    PRE_MANUTENCAO("Pré-manutenção"), POS_MANUTENCAO("Pós-manutenção");
    private String label;

    private TipoDespesa(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
