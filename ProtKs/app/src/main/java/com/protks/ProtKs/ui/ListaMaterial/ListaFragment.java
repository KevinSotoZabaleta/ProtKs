package com.protks.ProtKs.ui.ListaMaterial;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.protks.ProtKs.R;
import com.protks.ProtKs.model.ClaseMaterial;

import java.util.ArrayList;
import java.util.List;

public class ListaFragment extends Fragment {

    private List<ClaseMaterial> listaClaMate = new ArrayList<ClaseMaterial>();
    ArrayAdapter<ClaseMaterial> arrayAdapterClaseMaterial;

    private ListaViewModel mViewModel;
    ListView listaFire;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static ListaFragment newInstance() {
        return new ListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_fragment, container, false);
        listaFire = (ListView)view.findViewById(R.id.lista_material);
        listarDatos();

        return view;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);
        // TODO: Use the ViewModel
    }

}
