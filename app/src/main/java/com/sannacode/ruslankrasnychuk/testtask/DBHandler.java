package com.sannacode.ruslankrasnychuk.testtask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String USER_ID = "userId";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "contactName";
    private static final String KEY_EMAIL = "contactEmail";
    private static final String KEY_PHONE = "contactPhone";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = " CREATE TABLE "
                + TABLE_CONTACTS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PHONE + " TEXT, "
                + USER_ID + " TEXT " + " ); ";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContacts(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, contact.getId());
        values.put(KEY_NAME, contact.getContactName());
        values.put(KEY_EMAIL, contact.getContactEmail());
        values.put(KEY_PHONE, contact.getContactPhone());
        values.put(USER_ID, contact.getUserId());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public ContactModel getContact(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{ KEY_PHONE,
                        KEY_NAME, KEY_EMAIL, KEY_PHONE, USER_ID}, KEY_PHONE + " = ? ",
                new String[]{phone}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
           ContactModel contact = new ContactModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return contact;
    }

    public List<ContactModel> getAllContacts(String userId) {
        List<ContactModel> contactsList = new ArrayList<ContactModel>();
        String selectQuery = " SELECT * FROM " + TABLE_CONTACTS + " WHERE "+ USER_ID + " = " + "'"+userId+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ContactModel contact = new ContactModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setContactName(cursor.getString(1));
                contact.setContactEmail(cursor.getString(2));
                contact.setContactPhone(cursor.getString(3));
                contact.setUserId(cursor.getString(4));
                contactsList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactsList;
    }

    public int getContactCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public List<ContactModel> sortContact(String userId) {
        List<ContactModel> contactsList = new ArrayList<ContactModel>();
        String sortQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE "+ USER_ID + " = " + "'"+userId+"'" + " ORDER BY " + KEY_NAME  ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sortQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ContactModel contact = new ContactModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setContactName(cursor.getString(1));
                contact.setContactEmail(cursor.getString(2));
                contact.setContactPhone(cursor.getString(3));
                contact.setUserId(cursor.getString(4));
                contactsList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactsList;
    }

    public int updateContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contactModel.getContactName());
        values.put(KEY_EMAIL, contactModel.getContactEmail());
        values.put(KEY_PHONE, contactModel.getContactPhone());

        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
        new String[]{String.valueOf(contactModel.getId())});
    }

    public void deleteContact(ContactModel contactModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
        new String[] { String.valueOf(contactModel.getId()) });
        db.close();
    }
}
