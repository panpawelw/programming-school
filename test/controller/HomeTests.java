//package controller;
//
//import mockDAOs.MockLastSolutionDAO;
//import org.junit.Test;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.mock.web.MockServletContext;
//import pl.pjm77.controller.Home;
//
//public class HomeTests {
//
//    @Test
//    public void firstTest() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        MockServletContext servletContext = new MockServletContext();
//        servletContext.addInitParameter("number-solutions", "6");
//
//        Home home = new Home() {
//            @Override
//            public void init() {
//                System.out.println("whatever");
//                lastSolutionDAO = new MockLastSolutionDAO();
//            }
//        };
//
//        home.doGet(request, response);
//    }
//}