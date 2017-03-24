package com.sannacode.ruslankrasnychuk.testtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AddContact extends DialogFragment implements View.OnClickListener{

    Button btnSave;
    Button btnDelete;

    EditText editTextAddName;
    EditText editTextAddPhone;
    EditText editTextAddEmail;
    ContactModel contactModel;
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_contact, container, false);
        editTextAddName = (EditText)view.findViewById(R.id.editTextAddName);
        editTextAddPhone = (EditText)view.findViewById(R.id.editTextAddPhone);
        editTextAddEmail = (EditText)view.findViewById(R.id.editTextAddEmail);
        getDialog().setTitle("Додати контакт");
        tv = (TextView) view.findViewById(R.id.tv);
        btnSave = (Button)view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnDelete = (Button)view.findViewById(R.id.btnClose);
        btnDelete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                if (editTextAddName.getText().toString().isEmpty() || editTextAddPhone.getText().toString().isEmpty()) {
                    tv.setVisibility(View.VISIBLE);
                } else {
                    DBHandler db = new DBHandler(getContext());
                    contactModel = new ContactModel(editTextAddName.getText().toString(), editTextAddPhone.getText().toString(), editTextAddEmail.getText().toString(), ContactModelData.getOurInstance().getUserId());
                    db.addContacts(contactModel);
                    editTextAddName.getText().clear();
                    editTextAddPhone.getText().clear();
                    editTextAddEmail.getText().clear();
                    Intent intent = new Intent(getContext(), GeneralActivity.class);
                    startActivity(intent);
                    getDialog().dismiss();
                }
            break;
            case R.id.btnClose:
                getDialog().dismiss();
            break;
        }
    }
}
