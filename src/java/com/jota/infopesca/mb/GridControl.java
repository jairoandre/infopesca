/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.business.GenericBC;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

/**
 * Classe genérica para manutenção de CRUD simples.
 *
 * @author root
 */
public abstract class GridControl<T> implements Serializable {

    private final String OPACIDADE_NORMAL = "1";
    private final String OPACIDADE_50 = "0.5";
    private final Integer linesPerPage = 5;
    private Class<T> clazz;
    private T instance;
    private boolean showForm = false;
    private GenericBC<T> bc;
    private List<T> list;
    private Map<String, String> labels;
    private List<String> fieldNames;
    private List<String> formFields;
    private Boolean[] selectedItens;
    private boolean selectAll;
    private String opacity;
    List<HtmlSelectBooleanCheckbox> checkBoxes;
    private Integer page = 0;
    private Integer[] pages = {1};

    public GridControl(Class<T> clazz) {
        this.clazz = clazz;
        this.bc = new GenericBC<T>(clazz);
        opacity = "1";
        retrieveLabelsAndFields();
        updateList();
    }

    /*
     * PRIVATE METHODS
     */
    private void updateList() {
        try {
            list = bc.getList();
            selectedItens = new Boolean[list.size()];
            for (int i = 0; i < list.size(); i++) {
                selectedItens[i] = false;
            }
        } catch (Exception e) {
            //TODO: tratar
        }

    }

    private void retrieveLabelsAndFields() {
        this.formFields = new ArrayList<String>();
        this.fieldNames = new ArrayList<String>();
        this.labels = new HashMap<String, String>();
        Field[] fields = this.clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GridConfig.class)) {
                GridConfig annotation = field.getAnnotation(GridConfig.class);
                String fieldName = field.getName();
                this.fieldNames.add(fieldName);
                this.labels.put(fieldName, annotation.label());
                if (annotation.editable()) {
                    this.formFields.add(fieldName);
                }
            }
        }
    }

    /*
     * ACTIONS
     */
    public void preInclude(ActionEvent e) {
        try {
            instance = clazz.newInstance();
            showForm = true;
        } catch (InstantiationException ex) {
            Logger.getLogger(GridControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GridControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void confirm(ActionEvent e) {
        try {
            bc.persist(instance);
            updateList();
            showForm = false;
        } catch (Exception ex) {
            //TODO: Tratar
        }

    }

    public void cancel(ActionEvent e) {
        showForm = false;
    }

    public void deleteSelected(ActionEvent e) {
        try {
            for (int i = 0; i < selectedItens.length; i++) {
                if (selectedItens[i]) {
                    bc.remove(list.get(i));
                }
            }
            updateList();
            selectAll = false;
            opacity = OPACIDADE_NORMAL;
        } catch (Exception ex) {
            //TODO: Tratar
        }

    }

    public void checkAll(AjaxBehaviorEvent e) {
        if (selectedItens != null) {
            for (int i = 0; i < this.selectedItens.length; i++) {
                selectedItens[i] = selectAll;
            }
        }
        if (selectAll) {
            this.opacity = OPACIDADE_NORMAL;
        }
    }

    public void clickCheck(AjaxBehaviorEvent e) {
        int trues = 0;
        for (Boolean item : selectedItens) {
            if (item != null && item) {
                trues++;
            }
        }
        if (trues > 0) {
            selectAll = true;
            if (trues < selectedItens.length) {
                opacity = OPACIDADE_50;
            } else {
                opacity = OPACIDADE_NORMAL;
            }
        } else {
            opacity = "1";
            selectAll = false;
        }
    }

    /*
     * GETTERS AND SETTERS
     */
    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public boolean isShowForm() {
        return showForm;
    }

    public void setShowForm(boolean showForm) {
        this.showForm = showForm;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<String> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<String> formFields) {
        this.formFields = formFields;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getOpacity() {
        return opacity;
    }

    public void setOpacity(String opacity) {
        this.opacity = opacity;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public Boolean[] getSelectedItens() {
        return selectedItens;
    }

    public void setSelectedItens(Boolean[] selectedItens) {
        this.selectedItens = selectedItens;
    }

    /*
     * ARTIFICIAL GETTERS
     */
    /**
     * Verifica se a lista de itens está vazia.
     *
     * @return
     */
    public boolean isEmptyList() {
        return this.list != null ? this.list.isEmpty() : true;
    }

    /**
     * Retorna a quantidade de colunas da grid.
     *
     * @return
     */
    public int getColumnsCount() {
        return this.fieldNames != null ? this.fieldNames.size() + 1 : 0;
    }

    /**
     * Verifica se há ao menos um item selecionado.
     *
     * @return
     */
    public boolean isAtLeastOneSelect() {
        for (Boolean thereIs : selectedItens) {
            if (thereIs) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isDate(String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.date();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }

    public boolean isCurrency(String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.currency();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }
    }

    public boolean isText(String fieldName) {
        return !isDate(fieldName) && !isCurrency(fieldName);
    }

    /**
     * Verifica se o campo é do tipo data.
     *
     * @param fieldName
     * @return
     */
    public boolean isRequired(String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            GridConfig annotation = field.getAnnotation(GridConfig.class);
            return annotation.required();
        } catch (Exception e) {
            System.out.println("Erro :" + e.getMessage());
            return false;
        }

    }
}
