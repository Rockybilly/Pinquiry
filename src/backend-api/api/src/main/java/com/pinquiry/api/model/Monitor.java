package com.pinquiry.api.model;

import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "monitors", schema = "public")
public class Monitor implements Serializable{

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User monUser;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long monId;

    private String type;


    public Monitor() {
    }

    public Monitor(String type) {
        this.type = type;
    }

    public User getUser_id() {
        return monUser;
    }

    public void setUser_id(User user) {
        this.monUser = user;
    }

    public long getMon_id() {
        return monId;
    }

    public void setMon_id(long mon_id) {
        this.monId = mon_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getJSON(){
        JSONObject jo = new JSONObject();
        jo.appendField("mon_id", this.monId);
        jo.appendField("mon_user", this.monUser.getId());
        jo.appendField("type", this.type);
        return jo;
    }
    public String toString(){
        JSONObject jo = new JSONObject();
        jo.appendField("mon_id", this.monId);
        jo.appendField("mon_user", this.monUser.getId());
        jo.appendField("type", this.type);
        return jo.toString();
    }
}
