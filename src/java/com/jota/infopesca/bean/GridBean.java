/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.business.GenericBC;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 *
 * @author André
 */
public abstract class GridBean implements Serializable{
    
    private int inserctionOrder;
    
    public abstract String getOutputTextLabel();
    
    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isDate(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.date();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se o campo é do tipo moeda.
     *
     * @param fieldName
     * @return
     */
    public boolean isCurrency(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.currency();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }

    public boolean isText(String fieldName) {
        return !isDate(fieldName) && !isCurrency(fieldName) && !isEnum(fieldName) && !isBean(fieldName);
    }
    private Map<String, String> masks = new HashMap<String, String>();

    public String getMask(String fieldName) {
        if (masks.get(fieldName) == null) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                GridConfig annotation = field.getAnnotation(GridConfig.class);
                masks.put(fieldName, annotation.mask());
            } catch (Exception e) {
                System.out.println("Erro :" + e.getMessage());
            }
        }
        return masks.get(fieldName);

    }

    public int getSize(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.size();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return 20;
        }
    }

    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isRequired(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.required();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }
    
    public boolean isEnum(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.enumerated();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }
    
    public List<Object> getEnumValues(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            List<Object> list = new ArrayList<Object>();
            list.addAll(Arrays.asList(field.getType().getEnumConstants()));
            return list;
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return null;
        }
    }
    
    public boolean isBean(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.listed();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }
    
    public List<Object> getBeanList(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            Class clazz = field.getType();
            GenericBC bc = new GenericBC(clazz);
            return bc.getList();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return null;
        }
    }
    
    public int getInsertionOrder() {
        return inserctionOrder;
    }

    public void setInsertionOrder(int inserctionOrder) {
        this.inserctionOrder = inserctionOrder;
    }
    
    public String getValidationRules(String fieldName){
        StringBuilder str = new StringBuilder();
        str.append(isRequired(fieldName) ? "required" : "");
        str.append(isDate(fieldName) ? " date" : "");
        return str.toString();
    }
    
    public void setParent(Object parent){
    }
    
}
