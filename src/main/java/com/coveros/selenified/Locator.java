/*
 * Copyright 2018 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

package com.coveros.selenified;

/**
 * Select a Locator for the element we are interacting with Available options
 * are: xpath, id, name, classname, css, paritallinktext, linktext, tagname
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 5/16/2018
 */
public enum Locator {
    XPATH, ID, NAME, CLASSNAME, CSS, PARTIALLINKTEXT, LINKTEXT, TAGNAME
}