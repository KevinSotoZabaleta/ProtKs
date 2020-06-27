package com.protks.ProtKs;


import com.google.firebase.database.FirebaseDatabase;

public class MyFirebaseApp extends android.app.Application{

    //CREA LA PERSISTENCIA CON LA BASE DE DATOS

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
