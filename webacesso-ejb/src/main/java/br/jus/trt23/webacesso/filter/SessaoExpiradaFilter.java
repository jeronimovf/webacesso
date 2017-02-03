package br.jus.trt23.webacesso.filter;

import br.jus.trt23.nucleo.constants.Constantes;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessaoExpiradaFilter implements Filter, Constantes
{	
        @Override
	public void init(FilterConfig config) throws ServletException
	{
	}

        @Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		try
		{
			HttpServletRequest request = (HttpServletRequest) req;
			// Verifica sessão
			if (isAjax(request) && !request.isRequestedSessionIdValid() && request.getSession(false) == null)
			{
				HttpServletResponse response = (HttpServletResponse) resp;
				response.getWriter().print(xmlPartialRedirectToPage(request, VIEW_SESSAO_EXPIRADA));								
				response.flushBuffer();
			}
			else
			{
				chain.doFilter(req, resp);
			}

		} 
		catch (IOException | ServletException e)
		{
			// redirect to error page
			HttpServletRequest request = (HttpServletRequest) req;								
			HttpServletResponse response = (HttpServletResponse) resp;

			if (!isAjax(request))
			{
				response.sendRedirect(request.getContextPath() + VIEW_SESSAO_EXPIRADA);
			} 
			else
			{
				response.getWriter().print(xmlPartialRedirectToPage(request, request.getContextPath()));
				response.flushBuffer();
			}
		}
	}

	private String xmlPartialRedirectToPage(HttpServletRequest request, String page)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(page).append("\"/></partial-response>");
		return sb.toString();
	}

	private boolean isAjax(HttpServletRequest request)
	{
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}