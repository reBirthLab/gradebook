/*
 * Copyright (C) 2015 Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rebirthlab.gradebook.security;

import org.apache.commons.codec.binary.Base64;
import java.nio.charset.Charset;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
public class BasicAuthenticationDecoder {

    public static String[] getCredentials(String authorization) {
        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(base64Credentials),
                    Charset.forName("UTF-8"));
            String[] values = credentials.split(":", 2);
            return values;
        } else {
            return null;
        }
    }

    public static String getUsername(String authorization) {
        String[] values = getCredentials(authorization);
        return values[0];
    }
    
    public static String getPasword(String authorization) {
        String[] values = getCredentials(authorization);
        return values[1];
    }
}
