/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author root
 */
@ManagedBean
@RequestScoped
public class NavegacaoMB implements Serializable{
    
    private String[] opcoes = {
        "Home",
        "Embarcações",
        "Funcionários"};
    
    private Map<String,String> paginas = new HashMap<String, String>();
    
    /** Creates a new instance of NavegacaoMB */
    public NavegacaoMB() {
        paginas.put("Home", "index");
        paginas.put("Embarcações", "embarcacao");
        paginas.put("Funcionários", "funcionario");
    }

    public String[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(String[] opcoes) {
        this.opcoes = opcoes;
    }
    
    public String acionarAcao(String acao){
        String conteudo = paginas.get(acao);
        if(conteudo == null){
            conteudo = paginas.get("Home");
        }
        return conteudo;
    }

}
