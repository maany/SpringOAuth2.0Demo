package org.ateam.common.model;

import java.io.Serializable;

/**
 * Created by OPSKMC on 8/19/15.
 */
public interface Parameterized extends Serializable{
    public String getParameter();
    public void setParameter(String parameter);
    public void setClient(Client client);
}
