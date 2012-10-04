/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.bean.pattern;

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

    public abstract String getOutputTextLabel();

    private GridConfig getAnnotation(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            return null;
        } else {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                GridConfig annotation = field.getAnnotation(GridConfig.class);
                return annotation;
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
                return null;
            }
        }
    }

    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isDate(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.date();
        } else {
            return false;
        }

    }

    public boolean isNotBigDecimal(String fieldName) {
        return !isCurrency(fieldName) && !isWeight(fieldName);
    }

    /**
     * Verifica se o campo é do tipo moeda.
     *
     * @param fieldName
     * @return
     */
    public boolean isCurrency(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.currency();
        } else {
            return false;
        }
    }

    public boolean isWeight(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.weight();
        } else {
            return false;
        }
    }
    
    public boolean isTextArea(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.textArea();
        } else {
            return false;
        }
    }

    public boolean isFlag(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.flag();
        } else {
            return false;
        }
    }

    public boolean isText(String fieldName) {
        return !isDate(fieldName) && !isCurrency(fieldName) && !isEnum(fieldName) && !isBean(fieldName) && !isFlag(fieldName) && getMask(fieldName).isEmpty() && !isWeight(fieldName) && !isTextArea(fieldName);
    }
    private Map<String, String> masks = new HashMap<String, String>();

    public String getMask(String fieldName) {
        if (masks.get(fieldName) == null) {
            GridConfig annotation = getAnnotation(fieldName);
            if (annotation != null) {
                masks.put(fieldName, annotation.mask());
            } else {
                masks.put(fieldName, "");
            }
        }
        return masks.get(fieldName);

    }

    public int getSize(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.size();
        } else {
            return 20;
        }
    }

    public String getLabel(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.label();
        } else {
            return "Não definido";
        }
    }

    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isRequired(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.required();
        } else {
            return false;
        }
    }

    public boolean isEnum(String fieldName) {
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.enumerated();
        } else {
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
        GridConfig annotation = getAnnotation(fieldName);
        if (annotation != null) {
            return annotation.listed();
        } else {
            return false;
        }
    }

    public List<Object> getBeanList(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            Class clazz = field.getType();
            GenericBC bc = new GenericBC(clazz);
            return bc.listAll();
        } catch (Exception e) {
            return null;
        }
    }

    public void setParent(Object parent) {
    }

    public abstract Long getId();

    @Override
    public boolean equals(Object o) {
        Class thisClass = this.getClass();
        if (o == null) {
            return false;
        }
        Class otherClass = o.getClass();
        if (!thisClass.equals(otherClass)) {
            return false;
        }
        GridBean other = (GridBean) o;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        } else {
            return super.hashCode();
        }

    }

    public int getHashCode() {
        return hashCode();
    }
}
