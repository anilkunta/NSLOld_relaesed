package com.nsl.beejtantra.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by admin on 4/18/2017.
 */

public class FfmIdStatusVo {
    @SerializedName("ffm_id")
    @Expose
    public String ffmId;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("stages")
    @Expose
    public String stages;
    @SerializedName("complaint_status")
    @Expose
    public String complaintStatus;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
