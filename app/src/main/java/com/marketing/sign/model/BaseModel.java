package com.marketing.sign.model;

import com.marketing.sign.db.annotation.Column;
import com.marketing.sign.db.annotation.Id;

/**
 * Created by shixq on 2017/7/2.
 */

public class BaseModel {
    public static final String ID = "_id";
    @Id
    @Column(name = ID)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
