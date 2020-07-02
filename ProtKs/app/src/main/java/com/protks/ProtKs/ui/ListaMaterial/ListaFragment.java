package com.protks.ProtKs.ui.ListaMaterial;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protks.ProtKs.R;
import com.protks.ProtKs.model.ClaseMaterial;
import com.protks.ProtKs.ui.material.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListaFragment extends Fragment {

    private List<ClaseMaterial> listaClaMate2 = new ArrayList<ClaseMaterial>();
    ArrayAdapter<ClaseMaterial> arrayAdapterClaseMaterial2;

    private ListaViewModel mViewModel;
    ListView listaFire2;
    ClaseMaterial materialSelected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference2;

    public static ListaFragment newInstance() {
        return new ListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_fragment, container, false);
        listaFire2 = (ListView)view.findViewById(R.id.lista_material);
        inicializarFirebase();
        listarDatos();

        listaFire2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materialSelected = (ClaseMaterial) parent.getItemAtPosition(position);
                //Material.nombre.setText(materialSelected.getNombre());


            }
        });


        return view;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2 = firebaseDatabase.getReference();
    }
    private void listarDatos() {
        databaseReference2.child("ClaseMaterial").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaClaMate2.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                    ClaseMaterial cm = objSnaptshot.getValue(ClaseMaterial.class);
                    listaClaMate2.add(cm);

                    arrayAdapterClaseMaterial2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listaClaMate2);
                    listaFire2.setAdapter(arrayAdapterClaseMaterial2);
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
        mViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);
        // TODO: Use the ViewModel
    }

}
