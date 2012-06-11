/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.Vale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 08404235783
 */
@ManagedBean
@SessionScoped
public class ValeMB extends HardGridControl<Vale> {

    public ValeMB() {
        super(Vale.class);
    }
    
    public String preIncluirVale(){
        this.setInstance(new Vale());
        return "vale";
    }

       
}
