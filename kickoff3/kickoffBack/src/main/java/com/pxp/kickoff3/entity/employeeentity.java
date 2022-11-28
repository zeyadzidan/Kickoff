package com.pxp.kickoff3.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class employeeentity {
    public int id;
    public String firstname;
    public String lastname;

    public employeeentity()
    {

    }
    @Id
    @GeneratedValue
    public int getId()
    {
        return id;
    }
    public void setid(int id)
    {
        this.id=id;
    }
    public String getfirstname()
    {
        return firstname;
    }
    public void setFirstname(String firstname)
    {
        this.firstname=firstname;
    }
    public String getlastname()
    {
        return lastname;
    }
    public void setlastname(String lastname)
    {
        this.lastname=lastname;
    }

}
