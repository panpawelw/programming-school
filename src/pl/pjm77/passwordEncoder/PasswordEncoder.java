package pl.pjm77.passwordEncoder;

public interface PasswordEncoder {

    String encodePassword(String password);
    boolean isPasswordValid(String password, String checkAgainst);

}