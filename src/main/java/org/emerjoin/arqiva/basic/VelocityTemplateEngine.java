package org.emerjoin.arqiva.basic;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.emerjoin.arqiva.core.ArqivaException;
import org.emerjoin.arqiva.core.components.TemplateEngine;
import org.emerjoin.arqiva.core.context.HTMLRenderingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author Mário Júnior
 */
public class VelocityTemplateEngine implements TemplateEngine {

    private VelocityEngine velocityEngine = null;
    private VelocityConfigurator velocityConfigurator = null;
    private static Logger log = LoggerFactory.getLogger(VelocityTemplateEngine.class);

    public VelocityTemplateEngine(){

        velocityEngine = new VelocityEngine();
        velocityConfigurator = new VelocityConfigurator();

    }

    public void run(HTMLRenderingContext htmlRenderingContext) {

        try{

            VelocityContext velocityContext = new VelocityContext(htmlRenderingContext.getValues());
            VelocityEngine velocityEngine = new VelocityEngine();

            velocityEngine.init(velocityConfigurator.getInitProperties());

            RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();
            runtimeServices.setProperty("velocimacro.library.autoreload",true);
            runtimeServices.setProperty("file.resource.loader.cache",false);
            runtimeServices.setProperty("velocimacro.permissions.allow.inline.to.replace.global",true);

            StringReader reader = new StringReader(htmlRenderingContext.getHtml());
            String tempateId = "templateId"+String.valueOf(Calendar.getInstance().getTimeInMillis());
            SimpleNode node = runtimeServices.parse(reader, tempateId);
            Template template = new Template();
            template.setRuntimeServices(runtimeServices);
            template.setData(node);
            template.initDocument();

            StringWriter writer = new StringWriter();
            template.merge(velocityContext,writer,velocityConfigurator.getMacroLibraries());
            htmlRenderingContext.updateHtml(writer.toString());

        }catch (ParseException ex){

            throw new ArqivaException("Template engine has thrown an exception during compilation",ex);

        }


    }

    public Object getConfigurator() {

        return  velocityConfigurator;

    }

    public void startComponent() {

        //Nothing to start

    }
}
