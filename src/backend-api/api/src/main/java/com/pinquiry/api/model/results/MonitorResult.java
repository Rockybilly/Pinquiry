package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.monitor.Monitor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitorResult.class, name = "http"),
        @JsonSubTypes.Type(value = ContentMonitorEndResult.class, name = "content"),
        @JsonSubTypes.Type(value = PingMonitorResult.class, name = "ping")
})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public abstract class MonitorResult {

    public enum ResultType {
        http, content, ping
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ResultType type;

    private long timestamp;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Monitor monitor;

    private boolean incident;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public boolean isIncident() {
        return incident;
    }

    public void setIncident(boolean incident) {
        this.incident = incident;
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
