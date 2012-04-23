/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author André
 */
public class QueryUtil<T> implements Serializable{
    
    private T sample;
    private Collection<String> fields;
    private Collection<String> operators;
    
    public QueryUtil(T sample){
        this.sample = sample;
        fields = new ArrayList<String>();
        operators = new ArrayList<String>();
    }
    
    public void addCriteria(String field, String operator){
        fields.add(field);
        operators.add(operator);
    }

    public Collection<String> getFields() {
        return fields;
    }

    public void setFields(Collection<String> fields) {
        this.fields = fields;
    }

    public Collection<String> getOperators() {
        return operators;
    }

    public void setOperators(Collection<String> operators) {
        this.operators = operators;
    }

    public T getSample() {
        return sample;
    }

    public void setSample(T sample) {
        this.sample = sample;
    }
    
    
    
}
