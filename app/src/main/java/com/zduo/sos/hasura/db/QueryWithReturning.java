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

import java.util.HashSet;

public abstract class QueryWithReturning<Q, R> {
    protected HashSet<String> retSet;

    public QueryWithReturning() {
        this.retSet = new HashSet<>();
    }

    public abstract Q fromRetSet(HashSet<String> retSet);

    public <T1> Q returning(PGField<R, T1> f1) {
        this.retSet.add(f1.getColumnName());
        return fromRetSet(this.retSet);
    }

    public <T1, T2> Q returning(PGField<R, T1> f1, PGField<R, T2> f2) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        return fromRetSet(this.retSet);
    }

    public <T1, T2, T3> Q returning(PGField<R, T1> f1, PGField<R, T2> f2, PGField<R, T3> f3) {
        this.retSet.add(f1.getColumnName());
        this.retSet.add(f2.getColumnName());
        this.retSet.add(f3.getColumnName());
        return fromRetSet(this.retSet);
    }

}
