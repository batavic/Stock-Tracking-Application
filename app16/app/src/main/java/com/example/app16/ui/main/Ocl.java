package com.example.app16.ui.main; 


/******************************
* Copyright (c) 2003,2020 Kevin Lano
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
* *****************************/
/* OCL library for Java version 8+ */ 


import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;

  public class Ocl implements OclInterface{

    public Ocl(){}

    public <T> ArrayList<T> initialiseSequence(T... args) {
      ArrayList<T> result = new ArrayList<T>();
      for (int i = 0; i < args.length; i++) {
        result.add(args[i]);
      }
      return result;
    }

    public  <T> ArrayList<T> copySequence(Collection<T> s) {
      ArrayList<T> result = new ArrayList<T>();
      result.addAll(s);
      return result;
    }

  }

  interface OclInterface{
    <T> ArrayList<T> initialiseSequence(T... args);
    <T> ArrayList<T> copySequence(Collection<T> s);
  }
