package org.ulpmm.univrav.serverclasses;

import java.util.*;


 public class SystemInformation
 {
 public static void main(String args[])
 {
 //on regroupe ici les properties du System
 Properties systemProperties = System.getProperties();

 //on creer un Enumerateur de l'ensemble des clé des propriétés
 Enumeration<?> enum2 = systemProperties.propertyNames();

 //tant qu'il y a des elements
 while (enum2.hasMoreElements())
 {

 String key = (String)enum2.nextElement();//on recupere la clé
 //on affiche la valeur associé a cette clé
 System.out.println(key + "\t -> \t" + systemProperties.getProperty(key));

 }
 }
 } 