/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos.hasura.db;

import com.google.gson.JsonArray;

public abstract class QueryWithProjection<Q, R> {
    protected JsonArray columns;

    public QueryWithProjection() {
        this.columns = new JsonArray();
    }

    public abstract Q fromColumns(JsonArray columns);

    public Q columns(SelectField<R> f1) {
        this.columns.add(f1.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44, SelectField<R> f45) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        this.columns.add(f45.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44, SelectField<R> f45, SelectField<R> f46) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        this.columns.add(f45.toQCol());
        this.columns.add(f46.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44, SelectField<R> f45, SelectField<R> f46, SelectField<R> f47) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        this.columns.add(f45.toQCol());
        this.columns.add(f46.toQCol());
        this.columns.add(f47.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44, SelectField<R> f45, SelectField<R> f46, SelectField<R> f47, SelectField<R> f48) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        this.columns.add(f45.toQCol());
        this.columns.add(f46.toQCol());
        this.columns.add(f47.toQCol());
        this.columns.add(f48.toQCol());
        return fromColumns(this.columns);
    }

    public Q columns(SelectField<R> f1, SelectField<R> f2, SelectField<R> f3, SelectField<R> f4, SelectField<R> f5, SelectField<R> f6, SelectField<R> f7, SelectField<R> f8, SelectField<R> f9, SelectField<R> f10, SelectField<R> f11, SelectField<R> f12, SelectField<R> f13, SelectField<R> f14, SelectField<R> f15, SelectField<R> f16, SelectField<R> f17, SelectField<R> f18, SelectField<R> f19, SelectField<R> f20, SelectField<R> f21, SelectField<R> f22, SelectField<R> f23, SelectField<R> f24, SelectField<R> f25, SelectField<R> f26, SelectField<R> f27, SelectField<R> f28, SelectField<R> f29, SelectField<R> f30, SelectField<R> f31, SelectField<R> f32, SelectField<R> f33, SelectField<R> f34, SelectField<R> f35, SelectField<R> f36, SelectField<R> f37, SelectField<R> f38, SelectField<R> f39, SelectField<R> f40, SelectField<R> f41, SelectField<R> f42, SelectField<R> f43, SelectField<R> f44, SelectField<R> f45, SelectField<R> f46, SelectField<R> f47, SelectField<R> f48, SelectField<R> f49) {
        this.columns.add(f1.toQCol());
        this.columns.add(f2.toQCol());
        this.columns.add(f3.toQCol());
        this.columns.add(f4.toQCol());
        this.columns.add(f5.toQCol());
        this.columns.add(f6.toQCol());
        this.columns.add(f7.toQCol());
        this.columns.add(f8.toQCol());
        this.columns.add(f9.toQCol());
        this.columns.add(f10.toQCol());
        this.columns.add(f11.toQCol());
        this.columns.add(f12.toQCol());
        this.columns.add(f13.toQCol());
        this.columns.add(f14.toQCol());
        this.columns.add(f15.toQCol());
        this.columns.add(f16.toQCol());
        this.columns.add(f17.toQCol());
        this.columns.add(f18.toQCol());
        this.columns.add(f19.toQCol());
        this.columns.add(f20.toQCol());
        this.columns.add(f21.toQCol());
        this.columns.add(f22.toQCol());
        this.columns.add(f23.toQCol());
        this.columns.add(f24.toQCol());
        this.columns.add(f25.toQCol());
        this.columns.add(f26.toQCol());
        this.columns.add(f27.toQCol());
        this.columns.add(f28.toQCol());
        this.columns.add(f29.toQCol());
        this.columns.add(f30.toQCol());
        this.columns.add(f31.toQCol());
        this.columns.add(f32.toQCol());
        this.columns.add(f33.toQCol());
        this.columns.add(f34.toQCol());
        this.columns.add(f35.toQCol());
        this.columns.add(f36.toQCol());
        this.columns.add(f37.toQCol());
        this.columns.add(f38.toQCol());
        this.columns.add(f39.toQCol());
        this.columns.add(f40.toQCol());
        this.columns.add(f41.toQCol());
        this.columns.add(f42.toQCol());
        this.columns.add(f43.toQCol());
        this.columns.add(f44.toQCol());
        this.columns.add(f45.toQCol());
        this.columns.add(f46.toQCol());
        this.columns.add(f47.toQCol());
        this.columns.add(f48.toQCol());
        this.columns.add(f49.toQCol());
        return fromColumns(this.columns);
    }
}
