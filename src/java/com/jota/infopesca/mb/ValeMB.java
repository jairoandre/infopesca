/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Funcionario;
import com.jota.infopesca.bean.Vale;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author 08404235783
 */
@ManagedBean
@ViewScoped
public class ValeMB extends HardGridControl<Vale> {
    
    private List<Vale> vales;
    
    private Funcionario vale;

    
    public ValeMB() {
        super(Vale.class);
    }
    
    public String preIncluirVale(){
        this.setInstance(new Vale());
        return "vale";
    }

    public void pesquisarVale(){
    }
       
}
