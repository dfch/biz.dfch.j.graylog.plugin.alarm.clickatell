// Generated with http://www.jsonschema2pojo.org/
package biz.dfch.j.clickatell.rest.coverage;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class Data {

    private boolean routable;
    private String destination;
    private double minimumCharge;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The routable
     */
    public boolean isRoutable() {
        return routable;
    }

    /**
     *
     * @param routable
     * The routable
     */
    public void setRoutable(boolean routable) {
        this.routable = routable;
    }

    /**
     *
     * @return
     * The destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     *
     * @param destination
     * The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     *
     * @return
     * The minimumCharge
     */
    public double getMinimumCharge() {
        return minimumCharge;
    }

    /**
     *
     * @param minimumCharge
     * The minimumCharge
     */
    public void setMinimumCharge(double minimumCharge) {
        this.minimumCharge = minimumCharge;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}