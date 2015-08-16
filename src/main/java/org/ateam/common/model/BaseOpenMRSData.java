package org.ateam.common.model;

/**
 * Created by OPSKMC on 8/13/15.
 */
public abstract class BaseOpenMRSData {
    User creator;
    boolean retired;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }
}
