/*
 * Copyright 2019 Coveros, Inc.
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

package com.coveros.selenified.utilities;

/**
 * A generic representation of a point, with two objects, X and Y. These
 * could be interpreted as Integers, Doubles, or truly anything depending
 * on the future needs.
 *
 * @param <X>
 * @param <Y>
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 5/13/2018
 */
public class Point<X, Y> {
    private X x;
    private Y y;

    /**
     * The generic constructor of the point representation, providing an
     * initial X and Y coordinate in some form.
     *
     * @param x - the X coordinate of the point
     * @param y - the Y coordinate of the point
     */
    public Point(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public void setX(X x) {
        this.x = x;
    }

    public void setY(Y y) {
        this.y = y;
    }
}