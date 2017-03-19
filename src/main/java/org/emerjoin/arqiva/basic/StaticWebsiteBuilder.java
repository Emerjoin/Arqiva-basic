package org.emerjoin.arqiva.basic;

import org.emerjoin.arqiva.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

/**
 * @author Mário Júnior
 */
@Named("static-website")
public class StaticWebsiteBuilder implements ProjectBuilder {

    private static Logger log = LoggerFactory.getLogger(StaticWebsiteBuilder.class);

    public void build(Arqiva arqivaInstance) {

        log.info("Building static website docs...");
        String targetDirectory =  arqivaInstance.getProject().getContext().getTargetDirectory();

        String staticWebsiteDirectory = targetDirectory+"/docs";
        log.info("Website output path : "+staticWebsiteDirectory);
        File staticWebsiteDirectoryFile = new File(staticWebsiteDirectory);

        try {

            if (staticWebsiteDirectoryFile.exists())
                cleanDirectory(staticWebsiteDirectoryFile);


        }catch (Exception ex){
            throw new ArqivaException(String.format("Failed to delete directory %s",staticWebsiteDirectory),ex);
        }

        //Re-create the directory
        staticWebsiteDirectoryFile.mkdirs();
        File basePath  = new File(arqivaInstance.getProject().getContext().getSourceDirectory());
        copyFilesFrom(basePath, basePath,staticWebsiteDirectoryFile,(file) -> {

            //Only approve files with extension different from .html and .md
            return !(file.getName().endsWith(".html")||file.getName().endsWith(".md"));

        });

        createIndexPage(arqivaInstance,staticWebsiteDirectoryFile);
        buildTopicPages(arqivaInstance,staticWebsiteDirectoryFile);

    }


    private void copyFilesFrom(File basePath, File origin, File destination, Function<File,Boolean> approver){

        File[] files = origin.listFiles();
        String destinationPath = destination.getAbsolutePath();

        for(File fileToCopy : files){

            if(!fileToCopy.exists())
                continue;

            //Not approved
            if(!approver.apply(fileToCopy))
                continue;

            if(fileToCopy.isDirectory()) {
                copyFilesFrom(basePath, fileToCopy, destination, approver);
                continue;
            }

            String fileRelativePath = getRelativePath(basePath,fileToCopy);
            File fileToCreate = new File(destinationPath+fileRelativePath);
            log.info(String.format("Copying file %s",fileRelativePath));
            FileUtils.copyFileTo(fileToCopy,fileToCreate);

        }

    }

    private String getRelativePath(File basePath, File file){

        int basePathLength = basePath.getAbsolutePath().length();
        String fileToCopyPath = file.getAbsolutePath();
        String fileRelativePath = fileToCopyPath.substring(basePathLength,fileToCopyPath.length());
        return fileRelativePath;

    }

    private void createIndexPage(Arqiva project, File websiteDirectory){

        File indexPage = new File(websiteDirectory+"/index.html");
        FileUtils.putFileContents(indexPage,project.renderIndexPage());

    }

    private void buildTopicPages(Arqiva arqiva, File websiteDirectory){

        File projectDirectory = new File(arqiva.getProject().getContext().getSourceDirectory());
        buildTopicPagesTo(arqiva,projectDirectory,projectDirectory,websiteDirectory);

    }


    private void buildTopicPagesTo(Arqiva arqiva, File basePath, File sourceDirectory, File dest) {

        File[] files = sourceDirectory.listFiles();
        for (File candidateFile : files) {

            if (!candidateFile.exists())
                continue;

            if (candidateFile.isDirectory()) {
                buildTopicPagesTo(arqiva, basePath, candidateFile, dest);
                continue;
            }

            if(!candidateFile.getName().endsWith(".md"))
                continue;

            TopicReference topicReference = TopicReference.get(candidateFile,arqiva.getProject());
            String relativePath = "/topics/"+topicReference.getUrl()+".html";
            File destinationFile = new File(dest.getAbsolutePath()+relativePath);
            log.info(String.format("Building topic %s",relativePath));
            FileUtils.putFileContents(destinationFile,arqiva.renderTopicPage(candidateFile));

        }

    }

    private void cleanDirectory(File directory){

        File[] files = directory.listFiles();
        for(File file : files){

            if(!file.exists())
                continue;

            if(file.getName().equals(".")||file.getName().equals(".."))
                continue;

            if(file.isDirectory()){
                cleanDirectory(file);
                continue;
            }

            file.delete();

        }

        directory.delete();

    }

}

