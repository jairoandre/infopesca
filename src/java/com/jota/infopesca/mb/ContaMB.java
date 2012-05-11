/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Conta;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 08404235783
 */
public class ContaMB {

    private Conta conta;

    public ContaMB() {
    }

    // ACTIONS
    public String novaConta(Viagem[] viagens) {
        conta = new Conta();
        List<Viagem> lista = new ArrayList<Viagem>();
        for (Viagem viagem : viagens) {
            if (!viagem.getFechada()) {
                lista.add(viagem);
            }
        }
        if (lista.isEmpty()) {
            FacesUtil.addError("Não foi possível criar a conta!");
            return null;
        } else {
            conta.setViagens(lista);
            return "manterConta";
        }
    }

    // GETTERS & SETTERS
    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
