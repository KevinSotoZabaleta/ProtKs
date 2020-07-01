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
import android.widget.AdapterView;
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

    private MaterialViewModel mViewModel;

    private List<ClaseMaterial> listaClaMate = new ArrayList<ClaseMaterial>();
    ArrayAdapter<ClaseMaterial> arrayAdapterClaseMaterial;

    ClaseMaterial materialSelected;
    // COMPONENTES XML
    ListView listaFire;
    EditText nombre, precio;
    Button guardar,actualizar,eliminar;

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
        actualizar = (Button)view.findViewById(R.id.bt_actualizar);
        eliminar = (Button)view.findViewById(R.id.bt_eliminar);
        listaFire = (ListView)view.findViewById(R.id.lista_material2);


        //EVENTO ONCLICK DEL BOTON GUARDAR
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

        //EVENTO ONCLICK DEL BOTON ACTUALIZAR
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaseMaterial cm = new ClaseMaterial();
                cm.setId(materialSelected.getId());
                cm.setNombre(nombre.getText().toString().trim());
                cm.setPrecio(precio.getText().toString().trim());
                databaseReference.child("ClaseMaterial").child(cm.getId()).setValue(cm);
                Toast.makeText(getActivity(), "Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajar();
            }
        });

        listaFire .setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materialSelected = (ClaseMaterial) parent.getItemAtPosition(position);
                nombre.setText(materialSelected.getNombre());
                precio.setText(materialSelected.getPrecio());
            }
        });


        return view;
    }
    

        //METODO PARA LLAMAR E INICIALIZAR LA BASE DE DATOS DE FIREBASE
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
        //METODO PARA VACIAR LOS EDITTEXT UNA VEZ UTILIZADOS
    private void limpiarCajar() {
        nombre.setText("");
        precio.setText("");
    }
        //VALIDA QUE NO HALLA CAMPOS VACIOS
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

    private void listarDatos() {
        databaseReference.child("ClaseMaterial").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaClaMate.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    ClaseMaterial cm = objSnaptshot.getValue(ClaseMaterial.class);
                    listaClaMate.add(cm);

                    arrayAdapterClaseMaterial = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listaClaMate);
                    listaFire.setAdapter(arrayAdapterClaseMaterial);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MaterialViewModel.class);
        // TODO: Use the ViewModel
    }

}
