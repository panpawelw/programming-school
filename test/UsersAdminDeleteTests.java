import com.panpawelw.controller.UsersAdminDelete;
import mockDAOs.MockUserDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import static org.junit.Assert.assertEquals;

public class UsersAdminDeleteTests {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletConfig config;
    private UsersAdminDelete usersAdminDelete;

    @Before
    public void setup() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        usersAdminDelete = new UsersAdminDelete();
    }

    @Test
    public void UsersAdminDeleteForwardTest() throws Exception {
        usersAdminDelete.setUserDAO(new MockUserDAO());
        request.setParameter("id", "0");
        usersAdminDelete.init(config);
        usersAdminDelete.doGet(request, response);
        assertEquals("/usersadminpanel", response.getForwardedUrl());
    }
}