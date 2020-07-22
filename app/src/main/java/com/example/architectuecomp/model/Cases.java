package com.example.architectuecomp.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cases {

    @SerializedName("new")
    @Expose
    private String _new;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("critical")
    @Expose
    private Integer critical;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getNew() {
        return _new;
    }

    public void setNew(String _new) {
        this._new = _new;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}