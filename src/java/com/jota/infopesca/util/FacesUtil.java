package com.jota.infopesca.util;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author 08404235783
 */
public class FacesUtil {
    
    public static void addError(String msg) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", msg));
    }
}
