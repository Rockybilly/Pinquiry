package com.pinquiry.api.model.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pinquiry.api.model.PostgreSQLEnumType;
import com.pinquiry.api.model.monitor.Monitor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = HTTPMonitorResult.class, name = "HTTP"),
        @JsonSubTypes.Type(value = ContentMonitorEndResult.class, name = "CONTENT")})
@TypeDef(name = "enum_type", typeClass = PostgreSQLEnumType.class)
@Entity
public abstract class MonitorResult {

    public enum ResultType {
        HTTP, CONTENT
    }

    @Type( type = "enum_type")
    @Enumerated(EnumType.STRING)
    private ResultType type;

    @JsonProperty("mon_id")
    private long monId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Monitor monitor;

    private boolean incident;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMonId() {
        return monId;
    }

    public void setMonId(long monId) {
        this.monId = monId;
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
}
