package com.example.ace.androidgitexample;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Ace on 12/25/2015.
 */
public class Contact {

    private String _name, _phone, _email, _address;
    private Uri _image_uri;
    public int _id;

    public Contact (String name, String phone, String email, String address, Uri image_uri)
    {
        _name = name;
        _phone = phone;
        _email = email;
        _address = address;
        _image_uri = image_uri;
    }
    public int getID() {return _id; }

    public String getName()
    {
        return _name;
    }

    public String getPhone()
    {
        return _phone;
    }

    public String getEmail()
    {
        return _email;
    }

    public String getAddress()
    {
        return _address;
    }

    public Uri getImageURI() {return _image_uri;}

}
