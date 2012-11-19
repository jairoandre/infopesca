/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

import com.jota.infopesca.bean.pattern.GridBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author 08404235783
 */
public class SoftGridControl<T> extends GridControl {

  private Object parent;
  private List<T> list;

  @Override
  protected List listAll() throws Exception {
    return list;
  }

  @Override
  protected final void init() {
    setList(list);
  }
  
  public SoftGridControl(Class<T> clazz, Collection<T> list, Object parent) {
    super(clazz);
    this.list = new ArrayList<T>(list);
    this.parent = parent;
    init();
    try {
      search();
    } catch (Exception e) {
      System.out.println("Erro na excecução da consulta: " + e.getMessage());
    }
  }

  @Override
  protected void add(Object obj) {
    ((GridBean) obj).setParent(parent);
    this.list.add((T) obj);
  }

  @Override
  protected void alter(Object obj) {
    // Nada
  }

  @Override
  protected void remove(Object obj) {
    this.list.remove((T) obj);
  }

}
