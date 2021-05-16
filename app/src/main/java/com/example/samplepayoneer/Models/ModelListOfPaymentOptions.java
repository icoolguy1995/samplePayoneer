package com.example.samplepayoneer.Models;

import com.google.gson.annotations.SerializedName;

public class ModelListOfPaymentOptions {


    @SerializedName("code")
    private String shortName;
    @SerializedName("label")
    private String fullName;
    @SerializedName("method")
    private String type;
    private String grouping;
    private String registration;
    private String recurrence;
    private boolean redirect;
    @SerializedName("links")
    private ModelCardInfo getCardInternalFeatures;

    public ModelCardInfo getModelCardInfo() {
        return getCardInternalFeatures;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGrouping() {
        return grouping;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public String getRegistration() {
        return registration;
    }

    public String getShortName() {
        return shortName;
    }

    public String getType() {
        return type;
    }

}
