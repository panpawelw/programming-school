import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.model.User;

public class UserTest {

    public static final long EXPECTED_ID = 0;
    public static final String EXPECTED_NAME = "whoever";
    public static final String EXPECTED_EMAIL = "whoever@whatever.com";
    public static final String EXPECTED_PASSWORD = "whatever";
    public static final int EXPECTED_GROUP_ID = 1;

    private User user;

    @Before
    public void setup() throws Exception {
        user = new User("whoever", "whoever@whatever.com",
                "whatever", 1);
    }

    @Test
    public void testUser() throws Exception {
        Assert.assertEquals(EXPECTED_ID, user.getId());
        Assert.assertEquals(EXPECTED_NAME, user.getName());
        Assert.assertEquals(EXPECTED_EMAIL, user.getEmail());
        Assert.assertEquals(EXPECTED_PASSWORD, user.getPassword());
        Assert.assertEquals(EXPECTED_GROUP_ID, user.getGroup_id());
    }
}