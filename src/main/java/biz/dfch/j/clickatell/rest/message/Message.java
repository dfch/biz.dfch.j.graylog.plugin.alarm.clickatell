// Generated with http://www.jsonschema2pojo.org/
package biz.dfch.j.clickatell.rest.message;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "accepted",
        "to",
        "apiMessageId"
})
public class Message {

    @JsonProperty("accepted")
    private boolean accepted;
    @JsonProperty("to")
    private String to;
    @JsonProperty("apiMessageId")
    private String apiMessageId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The accepted
     */
    @JsonProperty("accepted")
    public boolean isAccepted() {
        return accepted;
    }

    /**
     *
     * @param accepted
     * The accepted
     */
    @JsonProperty("accepted")
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     *
     * @return
     * The to
     */
    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to
     * The to
     */
    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @return
     * The apiMessageId
     */
    @JsonProperty("apiMessageId")
    public String getApiMessageId() {
        return apiMessageId;
    }

    /**
     *
     * @param apiMessageId
     * The apiMessageId
     */
    @JsonProperty("apiMessageId")
    public void setApiMessageId(String apiMessageId) {
        this.apiMessageId = apiMessageId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}