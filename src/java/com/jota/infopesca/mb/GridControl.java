/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.bean.GridBean;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author 08404235783
 */
public abstract class GridControl<T> implements Serializable {

    private final String OPACIDADE_NORMAL = "1";
    private final String OPACIDADE_50 = "0.5";
    private Class<T> clazz;
    private List<T> list;
    private T instance;
    private boolean showForm = false;
    private Boolean isNewRecord;
    private Map<String, String> labels;
    private List<String> fieldNames;
    private List<String> formFields;
    private Boolean[] selectedItens;
    private boolean selectAll;
    private String opacity;

    public GridControl(Class<T> clazz) {
        this.clazz = clazz;
        opacity = "1";
        retrieveLabelsAndFields();
    }

    protected abstract void add(T obj);

    protected abstract void alter(T obj);

    protected abstract void remove(T obj);

    protected abstract List<T> refresh();

    protected boolean validateInclude() {
        return true;
    }

    /**
     * Recupera os campos que são exibidos e trabalhados pelo grid.
     */
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

    protected void updateList() {
        try {
            list = refresh();
            selectedItens = new Boolean[list.size()];
            for (int i = 0; i < selectedItens.length; i++) {
                GridBean obj = (GridBean) list.get(i);
                obj.setInsertionOrder(i);
                selectedItens[i] = false;
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /*
     * ACTIONS
     */
    public void confirm(ActionEvent e) {
        try {
            if (isNewRecord) {
                if (validateInclude()) {
                    add(instance);
                } else {
                    return;
                }
            } else {
                alter(instance);
            }
            updateList();
            selectAll = false;
            showForm = false;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

    public void deleteSelected() {
        try {
            List<T> toRemove = new ArrayList<T>();
            for (int i = 0; i < list.size(); i++) {
                if (selectedItens[i]) {
                    toRemove.add(list.get(i));
                }
            }
            for (T obj : toRemove) {
                remove(obj);
            }
            updateList();
            selectAll = false;
            opacity = OPACIDADE_NORMAL;
        } catch (Exception ex) {
            //TODO: Tratar
        }

    }

    public void preAlter(ActionEvent e) {
        isNewRecord = false;
        for (int i = 0; i < selectedItens.length; i++) {
            if (selectedItens[i]) {
                instance = list.get(i);
                break;
            }
        }
        showForm = true;
    }

    public void preInclude(ActionEvent e) {
        isNewRecord = true;
        try {
            instance = clazz.newInstance();
            showForm = true;
        } catch (InstantiationException ex) {
            Logger.getLogger(HardGridControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HardGridControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancel(ActionEvent e) {
        showForm = false;
    }

    public void checkAll(AjaxBehaviorEvent e) {
        for (int i = 0; i < selectedItens.length; i++) {
            selectedItens[i] = selectAll;
        }
        if (selectAll) {
            this.opacity = OPACIDADE_NORMAL;
        }
    }

    public void clickCheck(AjaxBehaviorEvent e) {
        int trues = 0;
        for (boolean is : selectedItens) {
            if (is) {
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

    public Boolean getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(Boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
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
        for (boolean is : selectedItens) {
            if (is) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se há ao menos um item selecionado.
     *
     * @return
     */
    public boolean isOnlyOneSelect() {
        int count = 0;
        for (boolean is : selectedItens) {
            if (is) {
                count++;
            }
        }
        return count == 1;
    }
}
