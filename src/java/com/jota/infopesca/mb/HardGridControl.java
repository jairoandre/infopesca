/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jota.infopesca.mb;

/**
 * Classe genérica para manutenção de CRUD simples.
 *
 * @author root
 */
public abstract class HardGridControl<T> extends GridControl {

  public HardGridControl(Class<T> clazz) {
    super(clazz);
    init();
    try {
      search();
    } catch (Exception e) {
      System.out.println("Erro na excecução da consulta: " + e.getMessage());
    }
  }

  @Override
  protected void add(Object obj) {
    try {
      getBc().persist((T) obj);
    } catch (Exception e) {
      System.out.println(e);
    }

  }

  @Override
  protected void alter(Object obj) {
    try {
      getBc().update((T) obj);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @Override
  protected void remove(Object obj) {
    try {
      getBc().remove((T) obj);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
