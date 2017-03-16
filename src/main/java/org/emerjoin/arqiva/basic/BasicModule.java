package org.emerjoin.arqiva.basic;

import org.emerjoin.arqiva.core.Module;
import org.emerjoin.arqiva.core.Named;
import org.emerjoin.arqiva.core.Project;
import org.emerjoin.arqiva.core.ProjectBuilder;
import org.emerjoin.arqiva.core.components.MarkdownParser;
import org.emerjoin.arqiva.core.components.TemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mário Júnior
 */
@Named("arqiva-basic")
public class BasicModule implements Module {

    private MarkdownParser markdownParser = null;
    private TemplateEngine templateEngine = null;
    private ProjectBuilder projectBuilder = null;
    private static Logger log = LoggerFactory.getLogger(BasicModule.class);

    public void postConstruct() {

        log.info(":) arqiva-basic is booting up...");
        markdownParser = new CommonmarkParser();
        templateEngine = new VelocityTemplateEngine();
        projectBuilder = new StaticWebsiteBuilder();

    }

    public void overrideComponents(Project project) {

        log.info("Setting commonmark Markdown parser by Atlassian");
        project.getContext().setMarkdownParser(markdownParser);
        log.info("Setting apache Velocity template engine");
        project.getContext().setTemplateEngine(templateEngine);
        project.getContext().addBuilder(projectBuilder);
        log.info(":) arqiva-basic is ready!");

    }

    public void configureComponents(Project project) {

        //Nothing to configure

    }
}
