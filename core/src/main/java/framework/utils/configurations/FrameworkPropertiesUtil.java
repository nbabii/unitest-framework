package framework.utils.configurations;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * PropertiesUtil assists with reading in properties from multiple 
 * properties file using Spring configuration
 *
 */
public class FrameworkPropertiesUtil extends PropertyPlaceholderConfigurer {

    private Map<String, String> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        propertiesMap = new HashMap<String, String>();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            propertiesMap.put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    /**
     * get property by name
     * @param name	property name
     * @return	property value
     */
    public String getProperty(String name) {
        return propertiesMap.get(name);
    }

}
