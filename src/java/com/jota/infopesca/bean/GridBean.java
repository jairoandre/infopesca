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
public abstract class GridBean implements Serializable {

    private Long creationTime = new Date().getTime();

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
    
    public boolean isNotBigDecimal(String fieldName){
        return !isCurrency(fieldName) && !isWeight(fieldName);
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
            return false;
        }
    }

    public boolean isWeight(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.weight();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFlag(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.flag();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isText(String fieldName) {
        return !isDate(fieldName) && !isCurrency(fieldName) && !isEnum(fieldName) && !isBean(fieldName) && !isFlag(fieldName) && getMask(fieldName).isEmpty() && !isWeight(fieldName);
    }
    private Map<String, String> masks = new HashMap<String, String>();

    public String getMask(String fieldName) {
        if (masks.get(fieldName) == null) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                GridConfig annotation = field.getAnnotation(GridConfig.class);
                masks.put(fieldName, annotation.mask());
            } catch (Exception e) {
                return "";
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
            return false;
        }
    }

    public boolean isEnum(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.enumerated();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Object> getEnumValues(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            List<Object> list = new ArrayList<Object>();
            list.addAll(Arrays.asList(field.getType().getEnumConstants()));
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isBean(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.listed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<Object> getBeanList(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            Class clazz = field.getType();
            GenericBC bc = new GenericBC(clazz);
            return bc.getList();
        } catch (Exception e) {
            return null;
        }
    }

    public String getValidationRules(String fieldName) {
        StringBuilder str = new StringBuilder();
        str.append(isRequired(fieldName) ? "required" : "");
        str.append(isDate(fieldName) ? " date" : "");
        return str.toString();
    }

    public void setParent(Object parent) {
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public abstract Long getId();

    @Override
    public boolean equals(Object o) {
        Class thisClass = this.getClass();
        if(o == null){
            return false;
        }
        Class otherClass = o.getClass();
        if (!thisClass.equals(otherClass)) {
            return false;
        }
        GridBean other = (GridBean) o;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        } else if (this.getId() == null && other.getId() == null) {
            return this.creationTime == other.creationTime;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : creationTime.hashCode());
        return hash;
    }
    
    public int getHashCode(){
        return hashCode();
    }
    
    public String getLabel(String fieldName){
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.label();
        } catch (Exception e) {
            return "";
        }
    }
    
}
