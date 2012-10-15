/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.converters;

import com.jota.infopesca.bean.pattern.GridBean;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 08404235783
 */
@FacesConverter(value = "outputTextConverter")
public class OutputTextConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "-";
        }
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format((Date) value);
        } else if (value instanceof GridBean) {
            return ((GridBean) value).getOutputTextLabel();
        } else if (value instanceof Enum) {
            try {
                Method m = value.getClass().getDeclaredMethod("getLabel");
                return (String) m.invoke(value);
            } catch (Exception e) {
                System.out.println("Erro na recuperação de label da enumeração: " + e);
                return value.toString();
            }
        }else if (value instanceof Boolean){
            return ((Boolean) value) ? "Sim" : "Não";
        }
        String str = value.toString();
        if(str.length() > 50){
          str = str.substring(0,49);
        }
        return str;
    }
}
