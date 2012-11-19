/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.annotations.GridConfig;
import com.jota.infopesca.business.GenericBC;
import com.jota.infopesca.enums.TipoOperacao;
import com.jota.infopesca.util.QueryUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author 08404235783
 */
public abstract class GridControl<T> implements Serializable {

  private Class<T> clazz;
  private List<T> list;
  private T instance;
  private T sample;
  private Map<String, TipoOperacao> searchParams;
  private boolean showForm = false;
  private Boolean isNewRecord;
  private Map<String, String> labels;
  private List<String> fieldNames;
  private List<String> formFields;
  private List<String> columnFields;
  private Map<String, SoftGridControl> softControllers;
  private Map<String, String> softControllersLabels;
  private Map<String, Class> softControllersClass;
  private Map<String, String> getterNames;
  private Map<String, String> setterNames;
  private LazyDataModel<T> model;
  private GenericBC<T> bc;
  private DataTable dataTable = new DataTable();

  public GridControl(Class<T> clazz) {
    this.clazz = clazz;
    softControllers = new LinkedHashMap<String, SoftGridControl>();
    softControllersLabels = new HashMap<String, String>();
    softControllersClass = new HashMap<String, Class>();
    getterNames = new HashMap<String, String>();
    setterNames = new HashMap<String, String>();
    bc = new GenericBC<T>(clazz);
    retrieveLabelsAndFields();
    dataTable.setFirst(0);
  }

  protected abstract void add(T obj);

  protected abstract void alter(T obj);

  protected abstract void remove(T obj);

  protected void init(){};

  protected boolean validateInclude() {
    return true;
  }

  /**
   * Recupera os campos que são exibidos e trabalhados pelo grid.
   */
  private void retrieveLabelsAndFields() {
    this.formFields = new ArrayList<String>();
    this.fieldNames = new ArrayList<String>();
    this.columnFields = new ArrayList<String>();
    this.labels = new HashMap<String, String>();
    Field[] fields = this.clazz.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(GridConfig.class)) {
        GridConfig annotation = field.getAnnotation(GridConfig.class);
        String fieldName = field.getName();
        // Campos griddable não compõem o formulário normal de dados.
        if (annotation.griddable()) {
          softControllersLabels.put(fieldName, annotation.label());
          softControllersClass.put(fieldName, annotation.softGridClass());
          getterNames.put(fieldName, composeMethodName("get", fieldName));
          setterNames.put(fieldName, composeMethodName("set", fieldName));
          continue;
        }

        this.labels.put(fieldName, annotation.label());

        // Verifica se é visível na listagem
        if (annotation.columnVisible()) {
          this.columnFields.add(fieldName);
        }

        // Veirifica se é visível no form.
        if (!annotation.visible()) {
          continue;
        }

        this.fieldNames.add(fieldName);

        if (annotation.editable()) {
          this.formFields.add(fieldName);
        }
      }
    }
  }

  protected List<T> listAll() throws Exception {
    return bc.listAll();
  }

  public final void search() throws Exception {
    dataTable.setFirst(0);
    if (sample != null) {
      QueryUtil<T> query = new QueryUtil<T>(sample);
      for (String field : searchParams.keySet()) {
        query.addCriteria(field, searchParams.get(field));
      }
      list = bc.listByProperties(query);
    } else {
      list = listAll();
    }
    final int listSize = list.size();
    model = new LazyDataModel<T>() {

      @Override
      public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filter) {
        if (listSize > pageSize) {

          try {
            return list.subList(first, first + pageSize);
          } catch (IndexOutOfBoundsException e) {
            System.out.println("Tamanho da lista ultrapassado!");
            return list.subList(first, first + (listSize % pageSize));
          }
        } else {
          return list;
        }
      }
    };
    model.setRowCount(listSize);
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
      search();
      showForm = false;
    } catch (Exception ex) {
      System.out.println("Erro: " + ex.getMessage());
    }

  }

  public void delete() {
    try {
      remove(instance);
      search();
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  private String composeMethodName(String sufix, String field) {
    StringBuilder str = new StringBuilder();
    str.append(sufix);
    str.append(field.substring(0, 1).toUpperCase());
    str.append(field.substring(1));
    return str.toString();
  }

  private void mountSoftControllers() {
    Set<String> fields = softControllers.keySet();
    for (String field : fields) {
      try {
        Method getter = this.clazz.getDeclaredMethod(getterNames.get(field));
        Collection softList = (Collection) getter.invoke(instance);
        if (softList == null) {
          softList = new ArrayList();
          Method setter = this.clazz.getDeclaredMethod(setterNames.get(field), Collection.class);
          setter.invoke(instance, softList);
        }
        Class softClass = softControllersClass.get(field);
        SoftGridControl sgc = new SoftGridControl(softClass, softList, instance);
        softControllers.put(field, sgc);
      } catch (Exception e) {
        // TODO: Tratar;
      }
    }
  }

  public void preAlter() {
    isNewRecord = false;
    mountSoftControllers();
    showForm = true;
  }

  public void preDelete() {
    isNewRecord = false;
  }

  public void preInclude() {
    isNewRecord = true;
    try {
      instance = clazz.newInstance();
      mountSoftControllers();
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

  public List<String> getColumnFields() {
    return columnFields;
  }

  public void setColumnFields(List<String> columnFields) {
    this.columnFields = columnFields;
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

  public T getSample() {
    return sample;
  }

  public void setSample(T sample) {
    this.sample = sample;
  }

  public Map<String, TipoOperacao> getSearchParams() {
    return searchParams;
  }

  public void setSearchParams(Map<String, TipoOperacao> searchParams) {
    this.searchParams = searchParams;
  }

  public Boolean getIsNewRecord() {
    return isNewRecord;
  }

  public void setIsNewRecord(Boolean isNewRecord) {
    this.isNewRecord = isNewRecord;
  }

  public Map<String, SoftGridControl> getSoftControllers() {
    return softControllers;
  }

  public void setSoftControllers(Map<String, SoftGridControl> softControllers) {
    this.softControllers = softControllers;
  }

  public List<String> getSoftControllersSet() {
    List<String> set = new ArrayList<String>();
    set.addAll(softControllers.keySet());
    return set;
  }

  public Map<String, String> getSoftControllersLabels() {
    return softControllersLabels;
  }

  public LazyDataModel<T> getModel() {
    return model;
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

  public GenericBC<T> getBc() {
    return this.bc;
  }

  public DataTable getDataTable() {
    return dataTable;
  }

  public void setDataTable(DataTable dataTable) {
    this.dataTable = dataTable;
  }
  
}
