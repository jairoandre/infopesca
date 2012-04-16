/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import java.util.Map;
import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author 08404235783
 */
@ManagedBean
public class ErrorBean {

    private static final String BR = "n";

    public String getStackTrace() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> map = context.getExternalContext().getRequestMap();
        Throwable throwable = (Throwable) map.get("javax.servlet.error.exception");
        StringBuilder builder = new StringBuilder();
        builder.append(throwable.getMessage()).append(BR);

        for (StackTraceElement element : throwable.getStackTrace()) {
            builder.append(element).append(BR);
        }

        return builder.toString();
    }
}