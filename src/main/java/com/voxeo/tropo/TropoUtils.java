package com.voxeo.tropo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TropoUtils {
    
    public static String toPrettyString(Object instance) {
    
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().disableHtmlEscaping().serializeNulls();
        Gson gson = builder.create();
        return gson.toJson(instance);
    }
}
