package com.nenosoft.homecontroler.com.nenosoft.homecontroler.info;

import java.io.Serializable;

/**
 * Created by USMAN BUTT on 3/7/2015.
 */
public class Control_Info implements Serializable {

    String id;
    String Ctr_1;
    String Ctr_2;
    String Ctr_3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCtr_1() {
        return Ctr_1;
    }

    public void setCtr_1(String ctr_1) {
        Ctr_1 = ctr_1;
    }

    public String getCtr_2() {
        return Ctr_2;
    }

    public void setCtr_2(String ctr_2) {
        Ctr_2 = ctr_2;
    }

    public String getCtr_3() {
        return Ctr_3;
    }

    public void setCtr_3(String ctr_3) {
        Ctr_3 = ctr_3;
    }
}
