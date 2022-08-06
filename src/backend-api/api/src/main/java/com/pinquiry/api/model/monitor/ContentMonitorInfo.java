package com.pinquiry.api.model.monitor;

import com.pinquiry.api.model.PostgreSQLEnumType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
public class ContentMonitorInfo {
    public enum ContentProtocolType {
        HTTP, HTTPS
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ContentProtocolType protocol;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String uri;
    private int port;
    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = { @Parameter( name = "classname", value = "java.util.HaspMap" ) }
    )
    private Map<String,String> RequestHeaders;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ContentMonitor monitor;

    public ContentMonitorInfo() {
    }

    public ContentMonitorInfo(ContentMonitor monitor) {
        this.monitor = monitor;
    }

    public ContentProtocolType getProtocol() {
        return protocol;
    }

    public void setProtocol(ContentProtocolType protocol) {
        this.protocol = protocol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ContentMonitor getMonitor() {
        return monitor;
    }

    public void setMonitor(ContentMonitor monitor) {
        this.monitor = monitor;
    }
}
