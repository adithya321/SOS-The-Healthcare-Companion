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

final class SortFieldImpl<R> implements SortField<R> {

    private final String columnName;
    private final SortOrder order;
    private NullsOrder nullsOrder;

    SortFieldImpl(String columnName, SortOrder order, NullsOrder nullsOrder) {
        this.columnName = columnName;
        this.order = order;
        this.nullsOrder = nullsOrder;
    }

    @Override
    public final String getColumnName() {
        return columnName;
    }

    @Override
    public final SortOrder getOrder() {
        return order;
    }

    @Override
    public final NullsOrder getNullsOrder() {
        return nullsOrder;
    }

    @Override
    public final SortField<R> nullsFirst() {
        nullsOrder = NullsOrder.FIRST;
        return this;
    }

    @Override
    public final SortField<R> nullsLast() {
        nullsOrder = NullsOrder.LAST;
        return this;
    }
}
