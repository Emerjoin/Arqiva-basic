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

    public Parser.Builder getParserBuilder(){

        return parserBuilder;

    }

    public HtmlRenderer.Builder getHtmlRendererBuilder(){

        return htmlRendererBuilder;

    }

    public void addParserExtension(Extension extension){

        parserExtensions.add(extension);

    }

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
