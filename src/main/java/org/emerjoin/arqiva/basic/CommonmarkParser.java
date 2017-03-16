package org.emerjoin.arqiva.basic;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.emerjoin.arqiva.core.components.MarkdownParser;

/**
 * @author Mário Júnior
 */
public class CommonmarkParser implements MarkdownParser {

    private CommonmarkParserConfigurator cmarkerConfigurator = null;
    private Parser parser = null;
    private HtmlRenderer htmlRenderer;

    public CommonmarkParser(){

        cmarkerConfigurator = new CommonmarkParserConfigurator();

    }

    public String toHTML(String input) {

        Node node = parser.parse(input);
        return htmlRenderer.render(node);

    }

    public Object getConfigurator() {

        return cmarkerConfigurator;

    }

    public void startComponent() {

        parser = cmarkerConfigurator.createParser();
        htmlRenderer = cmarkerConfigurator.createHTMLRenderer();

    }
}
