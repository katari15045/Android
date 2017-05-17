package com.example.sakethkatari.firebaseaccess;

/**
 * Created by Saketh Katari on 17-05-2017.
 */

public class User
{
    private String name;
    private String address;

    public User()
    {

    }

    public void create(String inpName, String inpAddress)
    {
        name = inpName;
        address = inpAddress;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }
}
