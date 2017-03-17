# Arqiva-basic
Arqiva module to build docs with apache Velocity template engine + Commonmark markdown parser by Atlassian


# Configurators
Arqiva allows modules to set configurations on build components (Markdown parser and Template engine) via configurators.
This module implements the build components contracts using Apache-Velocity as the template engine and Commonmark by Atlassian as the 
markdown parser. Both of the build components implementations provided by this module have configurator beans.


## Apache Velocity
The __VelocityTemplateEngine__ class implements the __TemplateEngine__ contract.
The configurator type for it is __VelocityConfigurator__ and provides the following public API:

```java
    
    //Gets the init properties that will be used to init the VelocityEngine instance.
    public Properties getInitProperties();

    //Gets the macro-libraries list to be used when merging template to context.
    public List<String> getMacroLibraries();

```

To understand the purpose of the *init properties* and the *macro-libraries* you may read the apache velocity API docs 
[here](https://velocity.apache.org/engine/1.7/apidocs/).

### Configuring apache velocity from another module
This is an example of how to configure apache velocity from another module:
```java

    @Named("example-module")
    public class BasicModule implements Module {
    
        public void postConstruct() { }
    
        public void overrideComponents(Project project) {}
    
        public void configureComponents(Project project) {
    
             VelocityConfigurator configurator = project.getContext().getTemplateEngine().getConfigurator();
             //Add init properites
             //Add macro-libraries
    
        }
    }


```

## Commonmark by Atlassian
The __CommonmarkParser__ class implements the __MarkdownParser__ contract.
The configurator type for it is __CommonmarkParserConfigurator__ and provides the following public API:

```java

      /**
       * Gets the Parser.Builder instance that will be used to build the Parser instance.
       * Extensions must be added using {@link #addParserExtension(Extension)}.
       */
      public Parser.Builder getParserBuilder();

      /**
       * Gets the HtmlRenderer.Builder instance that will be used to build the HtmlRenderer instance.
       * Extensions must be added using {@link #addRendererExtension(Extension)}.
       */
      public HtmlRenderer.Builder getHtmlRendererBuilder();
      
       /**
        * Adds an {@link Extension} instance to the list to be passed to the current Parser.Builder instance.
        * @param extension the extension to be added
        */
      public void addParserExtension(Extension extension);
      
      /**
       * Adds an {@link Extension} instance to the list to be passed to the current HtmlRenderer.Builder instance.
       * @param extension the extension to be added
       */
      public void addRendererExtension(Extension extension);

```

### Configuring Commonmark from another module
This is an example of how to configure Commonmark from another module:
```java

    @Named("example-module")
    public class BasicModule implements Module {
    
        public void postConstruct() { }
    
        public void overrideComponents(Project project) {}
    
        public void configureComponents(Project project) {
    
             CommonmarkParserConfigurator configurator = project.getContext().getMarkdownParser().getConfigurator();
             Parser.Builder mdParserBuilder = configurator.getParserBuilder();
             HtmlRenderer.Builder mdHtmlRenderer = configurator.getHtmlRendererBuilder();
             
             //Add markdown parser extensions
             //Add html renderer extensions
             //do Whatever you want with the the HtmlRenderer.Builder and or the Parser.Builder.
    
        }
    }

```