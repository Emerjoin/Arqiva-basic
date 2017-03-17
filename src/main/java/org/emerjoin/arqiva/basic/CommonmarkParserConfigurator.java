package org.emerjoin.arqiva.basic;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Mário Júnior
 */
public class CommonmarkParserConfigurator {

    private List<Extension> parserExtensions = new ArrayList<Extension>();
    private List<Extension> rendererExtensions = new ArrayList<Extension>();
    private Parser.Builder parserBuilder = new Parser.Builder();
    private HtmlRenderer.Builder htmlRendererBuilder = new HtmlRenderer.Builder();


    /**
     * Gets the Parser.Builder instance that will be used to build the Parser instance.
     * Extensions must be added using {@link #addParserExtension(Extension)}.
     * @return the Parser.Builder instance to be used to create the Parser instance.
     */
    public Parser.Builder getParserBuilder(){

        return parserBuilder;

    }

    /**
     * Gets the HtmlRenderer.Builder instance that will be used to build the HtmlRenderer instance.
     * Extensions must be added using {@link #addRendererExtension(Extension)}.
     * @return the HtmlRenderer.Builder instance to be used to create the HtmlRenderer instance.
     */
    public HtmlRenderer.Builder getHtmlRendererBuilder(){

        return htmlRendererBuilder;

    }

    /**
     * Adds an {@link Extension} instance to the list to be passed to the current Parser.Builder instance.
     * @param extension the extension to be added
     */
    public void addParserExtension(Extension extension){

        parserExtensions.add(extension);

    }

    /**
     * Adds an {@link Extension} instance to the list to be passed to the current HtmlRenderer.Builder instance.
     * @param extension the extension to be added
     */
    public void addRendererExtension(Extension extension){

        rendererExtensions.add(extension);

    }

    protected Collection<Extension> getParserExtensions(){

        return parserExtensions;

    }

    protected Collection<Extension> getRendererExtensions(){

        return rendererExtensions;

    }

    protected Parser createParser(){

        parserBuilder.extensions(parserExtensions);
        return parserBuilder.build();

    }

    protected HtmlRenderer createHTMLRenderer(){

        htmlRendererBuilder.extensions(rendererExtensions);
        return htmlRendererBuilder.build();

    }
}
