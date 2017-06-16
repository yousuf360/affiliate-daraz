package com.affiliate.retrofitModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousu on 6/9/2017.
 */
public class Entity {
    String name;
    List<String> ids;

    public Entity(String name, List<String> ids) {
        this.name = name;
        this.ids = ids;
    }

    public Entity(){
        ids = new ArrayList<String>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

