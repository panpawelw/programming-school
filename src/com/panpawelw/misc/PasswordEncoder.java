package com.panpawelw.misc;

import mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean isPasswordValid(String password, String checkAgainst) {
        return true;
    }
}