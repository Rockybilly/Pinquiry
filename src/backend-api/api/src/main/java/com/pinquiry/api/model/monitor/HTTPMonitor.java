package com.pinquiry.api.model.monitor;

import com.pinquiry.api.model.PostgreSQLEnumType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Map;


@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public class HTTPMonitor extends Monitor{
    public enum ProtocolType {
        HTTP, HTTPS
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ProtocolType protocol;

    private String server;


    private String uri;
    private int port;
    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @Parameter( name = "classname", value = "java.util.HashMap" ) }
    )
    private Map<String,String> RequestHeaders;

    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @Parameter( name = "classname", value = "java.util.HashMap" ) }
    )
    private Map<String,String> ResponseHeaders;
    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @Parameter( name = "classname", value = "java.util.Arraylist" ) }
    )
    private List<Integer> successCodes;

    public HTTPMonitor() {
        this.setType(MonitorType.HTTP);
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, String> getRequestHeaders() {
        return RequestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        RequestHeaders = requestHeaders;
    }

    public Map<String, String> getResponseHeaders() {
        return ResponseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        ResponseHeaders = responseHeaders;
    }

    public List<Integer> getSuccessCodes() {
        return successCodes;
    }

    public void setSuccessCodes(List<Integer> successCodes) {
        this.successCodes = successCodes;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
