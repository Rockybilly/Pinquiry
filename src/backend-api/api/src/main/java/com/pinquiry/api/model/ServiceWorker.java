package com.pinquiry.api.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class ServiceWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ip;

    private Timestamp lastActive;

    private boolean active;

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
}
