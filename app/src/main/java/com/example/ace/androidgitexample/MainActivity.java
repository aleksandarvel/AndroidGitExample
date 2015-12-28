package com.example.ace.androidgitexample;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button addContact;
    private EditText enterName, enterPhone, enterAddress, enterEmail;
    List<Contact> Contacts = new ArrayList<Contact>();
    TabHost th1;
    ListView contactList;
    ImageView contactImageView;
    Uri imageuri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.jpg");
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addContact = (Button) findViewById(R.id.addContact);
        enterName = (EditText) findViewById(R.id.enterName);
        addContact.setEnabled(false);
        addContact = (Button) findViewById(R.id.addContact);
        enterPhone = (EditText) findViewById(R.id.enterPhone);
        enterAddress = (EditText) findViewById(R.id.enterAddress);
        enterEmail = (EditText) findViewById(R.id.enterEmail);
        contactList = (ListView) findViewById(R.id.listView);
        contactImageView = (ImageView) findViewById(R.id.ImageViewContactImage);

        dbHandler = new DatabaseHandler(getApplicationContext());


        th1 = (TabHost) findViewById(R.id.tabHost);
        th1.setup();
        TabHost.TabSpec tabspec = th1.newTabSpec("creator");
        tabspec.setContent(R.id.creatorTab);
        tabspec.setIndicator("Add Contact");
        th1.addTab(tabspec);

        TabHost th2 = (TabHost) findViewById(R.id.tabHost);
        th1.setup();
        TabHost.TabSpec tabspec2 = th1.newTabSpec("creator12");
        tabspec2.setContent(R.id.addressTab);
        tabspec2.setIndicator("Contacts List");
        th1.addTab(tabspec2);

        /* CHANGE TABHOST BACKGROUND COLOR
        for(int i=0;i<th1.getTabWidget().getChildCount();i++)
        {
            th1.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#e91e63"));
        }
        th1.getTabWidget().setCurrentTab(1);
        th1.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#ffffff"));

        th1.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // TODO Auto-generated method stub
                for (int i = 0; i < th1.getTabWidget().getChildCount(); i++) {
                    th1.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff")); //unselected
                }
                th1.getTabWidget().getChildAt(th1.getCurrentTab()).setBackgroundColor(Color.parseColor("#e91e63")); // selected
            }
        }); */

        contactImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select contact image"), 1);
            }
        });

        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                addContact.setEnabled(!enterName.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        addContact.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                addContacts(enterName.getText().toString(), enterPhone.getText().toString(), enterEmail.getText().toString(), enterAddress.getText().toString(), imageuri);
                Toast.makeText(getApplicationContext(), enterName.getText().toString() + "has been added to your Contacts!", Toast.LENGTH_SHORT).show();
            }
        });

        populateListView();
    }


    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1)

            {
                imageuri = data.getData();
                contactImageView.setImageURI(data.getData());

            }
        }
    }

    private void addContacts(String name, String phone, String email, String address, Uri imageuri) {

        dbHandler.createContact(new Contact(name, phone, email, address, imageuri));
        populateListView();
    }

    private void populateListView()
    {
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactList.setAdapter(adapter);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super(MainActivity.this, R.layout.listview_item, dbHandler.getAllContacts());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listview_item, parent, false);
            }

            Contact currentContact = dbHandler.getAllContacts().get(position);

            TextView name = (TextView) convertView.findViewById(R.id.cName);
            name.setText(currentContact.getName());

            TextView email = (TextView) convertView.findViewById(R.id.cEmail);
            email.setText(currentContact.getEmail());

            TextView phone = (TextView) convertView.findViewById(R.id.cPhone);
            phone.setText(currentContact.getPhone());

            TextView address = (TextView) convertView.findViewById(R.id.cAddress);
            address.setText(currentContact.getAddress());

            ImageView listimage = (ImageView) convertView.findViewById(R.id.listImageView);
            listimage.setImageURI(currentContact.getImageURI());



            return convertView;
        }
    }
}
