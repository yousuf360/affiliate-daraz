package com.affiliate.retrofitModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousu on 6/9/2017.
 */
public class DataPacket{
    int start;
    int limit;
    List<Entity> entity;
    public DataPacket(){
        this.entity = new ArrayList<Entity>();
    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Entity> getEntity() {
        return entity;
    }

    public void setEntity(List<Entity> entity) {
        this.entity = entity;
    }


}