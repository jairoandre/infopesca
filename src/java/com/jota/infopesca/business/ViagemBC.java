/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.business;

import com.jota.infopesca.bean.Tripulante;
import com.jota.infopesca.bean.Viagem;
import com.jota.infopesca.dao.GenericDAO;
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

    public List<Tripulante> carregarTripulacaoRecente(Viagem viagem) throws Exception {
        List<Tripulante> lista = new ArrayList<Tripulante>();
        if (viagem != null) {
            QueryUtil<Viagem> query = new QueryUtil<Viagem>(viagem);
            query.addCriteria("embarcacao", TipoOperacao.EQ);
            query.descOrder("inicio");
            GenericDAO<Viagem> dao = new GenericDAO<Viagem>();
            List<Viagem> viagens = dao.list(query);
            for (Viagem viag : viagens) {
                if (!viag.getTripulantes().isEmpty()) {
                    for (Tripulante oldTrip : viag.getTripulantes()) {
                        Tripulante newTrip = new Tripulante();
                        newTrip.setFuncionario(oldTrip.getFuncionario());
                        newTrip.setFuncao(oldTrip.getFuncao());
                        lista.add(newTrip);
                    }
                    break;
                }
            }

        }
        return lista;
    }
}
