package com.sannacode.ruslankrasnychuk.testtask;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import java.util.List;


public class GeneralActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    DialogFragment dialogFragmentShowInfo;
    DialogFragment dialogFragmentAddContact;
    TextView tvName;
    private static final int DIALOG_REQUEST_CODE = 1;
    Button btnAddContact;
    DBHandler dbHandler;
    Switch mySwitch;

    final ContactModelData contactModelData = ContactModelData.getOurInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        dialogFragmentShowInfo = new ContactProfile();
        dialogFragmentAddContact = new AddContact();
        btnAddContact = (Button)findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(this);
        //btnSort = (Button)findViewById(R.id.btnSort);
        //btnSort.setOnClickListener(this);
        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText(contactModelData.getUserName());
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDataSet();
        mAdapter = new Adapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        contactModelData.setPosition(position);
                        ContactModelData.getOurInstance().setId(contactModelData.getList().get(position).getId());
                        Log.d("position ", contactModelData.getPosition()+" "+ contactModelData.getId());
                        dialogFragmentShowInfo.show(getSupportFragmentManager(), "ShowInfo");
                    }
                })
        );
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbHandler = new DBHandler(buttonView.getContext());
                if(isChecked){
                    contactModelData.setList(dbHandler.sortContact(ContactModelData.getOurInstance().getUserId()));
                    mAdapter  = new Adapter(dbHandler.sortContact(ContactModelData.getOurInstance().getUserId()));
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    contactModelData.setList(dbHandler.getAllContacts(ContactModelData.getOurInstance().getUserId()));
                    mAdapter  = new Adapter(dbHandler.getAllContacts(ContactModelData.getOurInstance().getUserId()));
                    for (ContactModel contactModel : contactModelData.getList()){
                        Log.d("profile ", " " + contactModel.getId() + " " + contactModel.getUserId() + " "+ contactModel.getContactName());
                    }
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddContact:
                dialogFragmentAddContact.show(getSupportFragmentManager(), "AddContact");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
     GoogleApiClient mGoogleApiClient;
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    private List<ContactModel> getDataSet() {
        dbHandler = new DBHandler(this);
        contactModelData.setList(dbHandler.getAllContacts(ContactModelData.getOurInstance().getUserId()));
        return dbHandler.getAllContacts(ContactModelData.getOurInstance().getUserId());
    }


}
