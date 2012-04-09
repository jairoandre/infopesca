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
    
    private String page = "home.xhtml";
    
    /** Creates a new instance of NavegacaoMB */
    public NavegacaoMB() {
    }

    public void acionarAcao(String acao){
        page = acao + ".xhtml";
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
