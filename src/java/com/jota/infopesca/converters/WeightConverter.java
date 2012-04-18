/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.converters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author André
 */
@FacesConverter(value="weightConverter",forClass=BigDecimal.class)
public class WeightConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string != null && !string.isEmpty()){
            string = string.replace(".", "").replace(",", ".");
            return new BigDecimal(string);
        }else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        DecimalFormat df = new DecimalFormat("#,##0.0");
        BigDecimal obj = (BigDecimal) o;
        return df.format(obj.doubleValue());
    }
    
}
