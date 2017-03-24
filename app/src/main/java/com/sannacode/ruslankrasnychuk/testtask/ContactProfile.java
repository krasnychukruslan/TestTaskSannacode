package com.sannacode.ruslankrasnychuk.testtask;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactProfile extends DialogFragment implements View.OnClickListener {

    Button btnSaveInfo;
    Button btnDeleteInfo;
    Button btnCloseInfo;
    Button btnCall;
    EditText nameInfo;
    EditText phoneInfo;
    EditText emailInfo;
    DBHandler dbHandler;
    TextView tvInfo;
    List<ContactModel> list = new ArrayList<>();
    final ContactModelData contactModelData = ContactModelData.getOurInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_info, container, false);
        btnSaveInfo = (Button) view.findViewById(R.id.btnSaveInfo);
        btnSaveInfo.setOnClickListener(this);
        btnDeleteInfo = (Button) view.findViewById(R.id.btnDeleteInfo);
        btnDeleteInfo.setOnClickListener(this);
        btnCloseInfo = (Button) view.findViewById(R.id.btnCloseInfo);
        btnCloseInfo.setOnClickListener(this);
        btnCall = (Button) view.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);
        getDialog().setTitle("Contact info");
        tvInfo = (TextView)view.findViewById(R.id.tvInfo);
        nameInfo = (EditText) view.findViewById(R.id.nameInfo);
        phoneInfo = (EditText) view.findViewById(R.id.phoneInfo);
        emailInfo = (EditText) view.findViewById(R.id.emailInfo);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.add(contactModelData.getList().get(contactModelData.getPosition()));
        for (ContactModel contactModel : list) {
            nameInfo.setText(contactModel.getContactName());
            phoneInfo.setText(contactModel.getContactPhone());
            emailInfo.setText(contactModel.getContactEmail());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveInfo:
                if (phoneInfo.getText().toString().isEmpty() || nameInfo.getText().toString().isEmpty()) {
                    tvInfo.setVisibility(View.VISIBLE);
                } else {
                    dbHandler = new DBHandler(getContext());
                    ContactModel contactModelUpdate = new ContactModel(contactModelData.getId(), nameInfo.getText().toString(), phoneInfo.getText().toString(), emailInfo.getText().toString(), contactModelData.getUserId());
                    dbHandler.updateContact(contactModelUpdate);
                    Intent intent = new Intent(getContext(), GeneralActivity.class);
                    getDialog().dismiss();
                    startActivity(intent);
                }
                break;
            case R.id.btnCloseInfo:
                getDialog().dismiss();
                break;
            case R.id.btnDeleteInfo:
                ContactModel contactModelDel = new ContactModel(contactModelData.getId(), nameInfo.getText().toString(), phoneInfo.getText().toString(), emailInfo.getText().toString(), contactModelData.getUserId());
                dbHandler = new DBHandler(getContext());
                dbHandler.deleteContact(contactModelDel);
                Intent intentDel = new Intent(getContext(), GeneralActivity.class);
                getDialog().dismiss();
                startActivity(intentDel);
                break;
            case R.id.btnCall:
                if (!phoneInfo.getText().toString().isEmpty()){
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + phoneInfo.getText().toString()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getContext(),
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                } else {
                    getActivity().startActivity(callIntent);
                }} else Toast.makeText(getContext(), "No phone NUMBER", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
