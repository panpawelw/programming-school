package com.panpawelw.passwordEncoder;

import mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean isPasswordValid(String password, String checkAgainst) {
        return true;
    }
}