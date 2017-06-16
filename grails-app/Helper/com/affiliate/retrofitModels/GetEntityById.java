package com.affiliate.retrofitModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousu on 6/9/2017.
 */
public class GetEntityById {
    public  GetEntityById(){
        this.dataPacket = new DataPacket();
    }




   String action;
   DataPacket dataPacket;








    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DataPacket getDataPacket() {
        return dataPacket;
    }

    public void setDataPacket(DataPacket dataPacket) {
        this.dataPacket = dataPacket;
    }
}
