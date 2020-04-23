package pl.pjm77.passwordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public String encodePassword(String password) {
        return password;
    }

    @Override
    public boolean isPasswordValid(String password, String checkAgainst) {
        return password.equals(checkAgainst);
    }
}