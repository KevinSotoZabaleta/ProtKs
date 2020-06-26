package com.protks.ProtKs.ui.material;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protks.ProtKs.R;
import com.protks.ProtKs.model.ClaseMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Material extends Fragment {

    private List<ClaseMaterial> listaClaMate = new ArrayList<ClaseMaterial>();
    ArrayAdapter<ClaseMaterial> arrayAdapterClaseMaterial;

    private MaterialViewModel mViewModel;

    EditText nombre, precio;
    Button guardar;
    ListView listaFire;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static Material newInstance() {
        return new Material();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.material_fragment, container, false);

        inicializarFirebase();
        listarDatos();


        nombre = (EditText)view.findViewById(R.id.et_nombre);
        precio = (EditText)view.findViewById(R.id.et_precio);
        guardar = (Button)view.findViewById(R.id.bt_guardar);
        listaFire = (ListView)view.findViewById(R.id.lista_material2);



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String v_nombre = nombre.getText().toString();
                String v_precio = precio.getText().toString();

                if(v_nombre.equals("") || v_precio.equals("")){
                    validacion();
                }else{
                    ClaseMaterial cm = new ClaseMaterial();
                    cm.setId(UUID.randomUUID().toString());
                    cm.setNombre(v_nombre);
                    cm.setPrecio(v_precio);
                    databaseReference.child("ClaseMaterial").child(cm.getId()).setValue(cm);
                    Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_LONG).show();
                    limpiarCajar();
                }
            }
        });

        return view;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void listarDatos() {
        databaseReference.child("ClaseMaterial").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaClaMate.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    ClaseMaterial p = objSnaptshot.getValue(ClaseMaterial.class);
                    listaClaMate.add(p);

                    arrayAdapterClaseMaterial = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listaClaMate);
                    listaFire.setAdapter(arrayAdapterClaseMaterial);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
    public boolean onOptionsItemSelected(MenuItem item){

        String v_nombre = nombre.getText().toString();
        String v_precio = precio.getText().toString();

        switch (item.getItemId()){
            case R.id.action_settings:{
                Toast.makeText(getActivity(),"Settings", Toast.LENGTH_LONG).show();
                break;
            }


            case R.id.action_guardar:{
                if(v_nombre.equals("") || v_precio.equals("")){
                    validacion();
                }else{
                    Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_LONG).show();
                    limpiarCajar();
                    break;
                }
            }
            case R.id.action_modificar:{
                Toast.makeText(getActivity(), "Modificar", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.action_eliminar:{
                Toast.makeText(getActivity(), "Eliminar", Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
     */

    private void limpiarCajar() {
        nombre.setText("");
        precio.setText("");
    }

    private void validacion() {
        String v_nombre = nombre.getText().toString();
        String v_precio = precio.getText().toString();

        if(v_nombre.equals("")){
            nombre.setError("Required");
        }
        if(v_precio.equals("")){
            precio.setError("Required");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MaterialViewModel.class);
        // TODO: Use the ViewModel
    }

}
