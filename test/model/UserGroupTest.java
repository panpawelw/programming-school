package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.model.UserGroup;

public class UserGroupTest {

    public static final int EXPECTED_ID = 0;
    public static final String EXPECTED_NAME = "whatever";

    private UserGroup userGroup;

    @Before
    public void setup() throws Exception {
        userGroup = new UserGroup("whatever");
    }

    @Test
    public void testUserGroup() throws Exception {
        Assert.assertEquals(EXPECTED_ID, userGroup.getId());
        Assert.assertEquals(EXPECTED_NAME, userGroup.getName());
    }
}