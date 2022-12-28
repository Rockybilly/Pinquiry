package com.pinquiry.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "service_worker", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ip")
})
public class ServiceWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ip;

    private String name;

    private int port;

    private Timestamp lastActive;

    private boolean active;

    @JsonProperty("countryCode")
    private String location;

    @ElementCollection
    @CollectionTable(name="worker_monitors", joinColumns=@JoinColumn(name="id"))
    private List<Long> monIds;

    public ServiceWorker() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getLastActive() {
        return lastActive;
    }

    public void setLastActive(Timestamp lastActive) {
        this.lastActive = lastActive;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Long> getMonIds() {
        return monIds;
    }

    public void setMonIds(List<Long> monIds) {
        this.monIds = monIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
