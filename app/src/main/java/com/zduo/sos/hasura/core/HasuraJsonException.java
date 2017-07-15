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

package com.zduo.sos.hasura.core;

public class HasuraJsonException extends Exception {
    private static final long serialVersionUID = 1;
    private int code;

    /**
     * Construct a new HasuraJsonException with a particular error code.
     *
     * @param theCode    The error code to identify the type of exception.
     * @param theMessage A message describing the error in more detail.
     */
    public HasuraJsonException(int theCode, String theMessage) {
        super(theMessage);
        code = theCode;
    }

    /**
     * Construct a new HasuraJsonException with an external cause.
     *
     * @param message A message describing the error in more detail.
     * @param cause   The cause of the error.
     */
    public HasuraJsonException(int theCode, String message, Throwable cause) {
        super(message, cause);
        code = theCode;
    }

    /**
     * Construct a new HasuraJsonException with an external cause.
     *
     * @param cause The cause of the error.
     */
    public HasuraJsonException(Throwable cause) {
        super(cause);
        code = 0;
    }

    /**
     * Access the code for this error.
     *
     * @return The numerical code for this error.
     */
    public int getCode() {
        return code;
    }
}
