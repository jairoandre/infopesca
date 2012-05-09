/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.util;

import com.jota.infopesca.enums.TipoOperacao;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author André
 */
public class QueryUtil<T> implements Serializable {

    private T sample;
    private Collection<String> fields;
    private Collection<TipoOperacao> operators;

    public QueryUtil(T sample) {
        this.sample = sample;
        fields = new ArrayList<String>();
        operators = new ArrayList<TipoOperacao>();
    }
    
    public void addCriteria(String field, TipoOperacao operator) {
        fields.add(field);
        operators.add(operator);
    }

    public Collection<String> getFields() {
        return fields;
    }

    public void setFields(Collection<String> fields) {
        this.fields = fields;
    }

    public Collection<TipoOperacao> getOperators() {
        return operators;
    }

    public void setOperators(Collection<TipoOperacao> operators) {
        this.operators = operators;
    }

    public T getSample() {
        return sample;
    }

    public void setSample(T sample) {
        this.sample = sample;
    }

    private String getter(Object obj) {
        String field = (String) obj;
        StringBuilder getter = new StringBuilder();
        getter.append("get");
        getter.append(field.substring(0, 1).toUpperCase());
        getter.append(field.substring(1));
        return getter.toString();
    }

    public CriteriaQuery<T> createQuery(CriteriaBuilder builder) throws Exception {

        Class entityClass = sample.getClass();

        CriteriaQuery<T> query = builder.createQuery(entityClass);

        Root<T> root = query.from(entityClass);
        
        query.select(root);

        List<Predicate> predicados = new ArrayList<Predicate>();

        Object[] fieldsArray = this.fields.toArray();
        Object[] operatorsArray = this.operators.toArray();

        for (int i = 0; i < fieldsArray.length; i++) {
            Method m = entityClass.getDeclaredMethod(getter(fieldsArray[i]));
            Object value = m.invoke(sample);
            if (value != null) {
                switch ((TipoOperacao) operatorsArray[i]) {
                    case EQ:
                        predicados.add(builder.equal(root.get((String) fieldsArray[i]), value));
                        break;
                    case GT:
                        predicados.add(builder.greaterThan(root.<Comparable>get((String) fieldsArray[i]), (Comparable) value));
                        break;
                    case LT:
                        predicados.add(builder.lessThan(root.<Comparable>get((String) fieldsArray[i]), (Comparable) value));
                        break;
                    case GE:
                        predicados.add(builder.greaterThanOrEqualTo(root.<Comparable>get((String) fieldsArray[i]), (Comparable) value));
                        break;
                    case LE:
                        predicados.add(builder.lessThanOrEqualTo(root.<Comparable>get((String) fieldsArray[i]), (Comparable) value));
                        break;
                    default:
                        break;
                }
            }
        }

        if (!predicados.isEmpty()) {
            Predicate[] arrayPredicados = new Predicate[predicados.size()];
            predicados.toArray(arrayPredicados);
            query.where(arrayPredicados);
        }

        return query;
    }
}
