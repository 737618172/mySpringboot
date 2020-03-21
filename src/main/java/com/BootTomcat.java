package com;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

public class BootTomcat {

    public static void Run() throws ServletException {

        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8006);
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(
                webappDirLocation).getAbsolutePath());
        ctx.setReloadable(false);

        File additionWebInfClasses = new File("target/classes");

        String contextPath = "";
        ctx.setPath(contextPath);
        ctx.addLifecycleListener(new Tomcat.FixContextListener());
        ctx.setName("news-web");
        tomcat.getHost().addChild(ctx);

        WebResourceRoot resources = new StandardRoot(ctx);

        resources.addPreResources(new DirResourceSet(resources,
                "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(),
                "/"));
        ctx.setResources(resources);
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
