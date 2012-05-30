/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.business;

import com.jota.infopesca.bean.Tripulante;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.enums.TipoOperacao;
import com.jota.infopesca.util.QueryUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 08404235783
 */
public class ViagemBC extends GenericBC<Viagem> {

    public ViagemBC() {
        super(Viagem.class);
    }

    public List<Tripulante> carregarTripulacaoPassada(Viagem viagem) {
        List<Tripulante> lista = new ArrayList<Tripulante>();
        if (viagem != null) {
            QueryUtil<Viagem> query = new QueryUtil<Viagem>(viagem);
            query.addCriteria("data", TipoOperacao.EQ);
        }
        return lista;
    }
}
