package com.example.dell.appcuxa.ObjectModels;

public class UtilityObject {
    public String id;
    public String name;
    public String code;

    public UtilityObject(String id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
