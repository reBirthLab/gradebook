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
package com.rebirthlab.gradebook.application.security;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anastasiy Tovstik <anastasiy.tovstik@gmail.com>
 */
public class AuthenticationService {

    private String username;
    private String password;

    public boolean authenticate(String authCredentials) {

        if (null == authCredentials) {
            return false;
        }
        final String encodedUserPassword = authCredentials.replaceFirst("Basic"
                + " ", "");
        String usernameAndPassword = null;
        
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(
                    encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            Logger.getLogger(AuthenticationService.class.getName())
                    .log(Level.SEVERE, null, e);
        }
        
        final StringTokenizer tokenizer = new StringTokenizer(
                usernameAndPassword, ":");
        username = tokenizer.nextToken();
        password = tokenizer.nextToken();

        CurrentUser user = UserDataFinder.findDataBy(username);

        try {
            boolean authenticationStatus = user.getUsername().equals(username)
                    && user.getPassword().equals(password);
            return authenticationStatus;
        } catch (Exception e) {
            Logger.getLogger(AuthenticationService.class.getName())
                    .log(Level.SEVERE, "No such user in system", e);
            return false;
        }
    }

    public String getUsername(String authCredentials) {
        authenticate(authCredentials);
        return username;
    }

    public String getPassword(String authCredentials) {
        authenticate(authCredentials);
        return password;
    }
}
