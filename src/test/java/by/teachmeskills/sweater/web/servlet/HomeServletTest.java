package by.teachmeskills.sweater.web.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HomeServletTest {
    @Test
    void testDoGet() throws ServletException, IOException {
        HomeServlet homeServlet = new HomeServlet();
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        doNothing().when(requestDispatcher)
                .forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestDispatcher((String) any())).thenReturn(requestDispatcher);
        HttpServletRequestWrapper req = new HttpServletRequestWrapper(new HttpServletRequestWrapper(
                new HttpServletRequestWrapper(new HttpServletRequestWrapper(httpServletRequest))));
        homeServlet.doGet(req, new HttpServletResponseWrapper(new HttpServletResponseWrapper(
                new HttpServletResponseWrapper(new HttpServletResponseWrapper(mock(HttpServletResponse.class))))));
        verify(httpServletRequest).getRequestDispatcher((String) any());
        verify(requestDispatcher).forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
    }

    @Test
    void testDoGet2() throws ServletException, IOException {
        HomeServlet homeServlet = new HomeServlet();
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        doThrow(new IOException("An error occurred")).when(requestDispatcher)
                .forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestDispatcher((String) any())).thenReturn(requestDispatcher);
        HttpServletRequestWrapper req = new HttpServletRequestWrapper(new HttpServletRequestWrapper(
                new HttpServletRequestWrapper(new HttpServletRequestWrapper(httpServletRequest))));
        assertThrows(IOException.class,
                () -> homeServlet.doGet(req, new HttpServletResponseWrapper(new HttpServletResponseWrapper(
                        new HttpServletResponseWrapper(new HttpServletResponseWrapper(mock(HttpServletResponse.class)))))));
        verify(httpServletRequest).getRequestDispatcher((String) any());
        verify(requestDispatcher).forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
    }

    @Test
    void testDoGet3() throws ServletException, IOException {
        HomeServlet homeServlet = new HomeServlet();
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        doThrow(new ServletException("An error occurred")).when(requestDispatcher)
                .forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestDispatcher((String) any())).thenReturn(requestDispatcher);
        HttpServletRequestWrapper req = new HttpServletRequestWrapper(new HttpServletRequestWrapper(
                new HttpServletRequestWrapper(new HttpServletRequestWrapper(httpServletRequest))));
        assertThrows(ServletException.class,
                () -> homeServlet.doGet(req, new HttpServletResponseWrapper(new HttpServletResponseWrapper(
                        new HttpServletResponseWrapper(new HttpServletResponseWrapper(mock(HttpServletResponse.class)))))));
        verify(httpServletRequest).getRequestDispatcher((String) any());
        verify(requestDispatcher).forward((jakarta.servlet.ServletRequest) any(), (jakarta.servlet.ServletResponse) any());
    }
}