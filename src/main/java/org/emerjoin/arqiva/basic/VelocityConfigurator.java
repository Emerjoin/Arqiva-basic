package org.emerjoin.arqiva.basic;

import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Mário Júnior
 */
public class VelocityConfigurator {

    private Properties properties = null;
    private List<String> macroLibraries = null;

    public VelocityConfigurator(){

        properties = new Properties();
        macroLibraries = new ArrayList<String>();

    }


    public Properties getInitProperties(){

        return properties;

    }


    public List<String> getMacroLibraries(){

        return macroLibraries;

    }


}
