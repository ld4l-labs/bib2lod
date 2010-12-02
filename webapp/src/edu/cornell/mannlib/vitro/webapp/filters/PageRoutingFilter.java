/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vitro.webapp.controller.freemarker.PageController;
import edu.cornell.mannlib.vitro.webapp.dao.PageDao;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;
/**
 * This filter is intended to route requests to pages defined in the display model.
 * 
 * It should be last in the chain of filters since it will not call filters further
 * down the chain.
 * 
 * It should only be applied to requests, not forwards, includes or errors.
 */
public class PageRoutingFilter implements Filter{
    FilterConfig filterConfig;
    
    private final static Log log = LogFactory.getLog( PageRoutingFilter.class)
    ;
    protected final static String PAGE_CONTROLLER_NAME = "PageController";
    protected final static String HOME_CONTROLLER_NAME = "HomePageController";
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        this.filterConfig = arg0;
        log.debug("pageRoutingFilter setup");
    }
    
    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) 
        throws IOException, ServletException {        
        PageDao pageDao = getPageDao();        
        Map<String,String> urlMappings = pageDao.getPageMappings();
        
        // get URL without hostname or servlet context
        HttpServletResponse response = (HttpServletResponse) arg1;
        HttpServletRequest req = (HttpServletRequest) arg0;        
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        // check for first part of path
        // ex. /hats/superHat -> /hats
        String path1 = path;
        if( path != null && path.indexOf("/",1) > 0 ){           
            path1 = path.substring(0,path1.indexOf("/"));
        }
        
        String pageUri = urlMappings.get(path1);
        
        //try it with a leading slash
        if( pageUri == null ){
            pageUri = urlMappings.get("/"+path1);
        }
        
        if( pageUri != null && ! pageUri.isEmpty() ){
            log.debug(path + "is a request to a page defined in the display model as " + pageUri );
            		
            //add the pageUri to the request scope for use by the PageController
            PageController.putPageUri(req, pageUri);
            
            //This will send requests to HomePageController or PageController
            String controllerName = getControllerToForwardTo(req, pageUri, pageDao);            
            log.debug(path + " is being forwarded to controller " + controllerName);
            
            RequestDispatcher rd = filterConfig.getServletContext().getNamedDispatcher( controllerName );            
            rd.forward(req, response);
        }else{
            log.debug(path + "this isn't a request to a page defined in the display model, handle it normally.");
            chain.doFilter(arg0, arg1);
        }        
    }

    protected String getControllerToForwardTo(HttpServletRequest req,
            String pageUri, PageDao pageDao) {
        String homePageUri = pageDao.getHomePageUri();
        if( pageUri != null && pageUri.equals(homePageUri) )
            return HOME_CONTROLLER_NAME;
        else
            return PAGE_CONTROLLER_NAME;
    }

    protected PageDao getPageDao(){
        WebappDaoFactory wdf = (WebappDaoFactory) 
            filterConfig.getServletContext().getAttribute("webappDaoFactory");
        return wdf.getPageDao();
    }
        
    @Override
    public void destroy() {
       //nothing to do here        
    }
}
