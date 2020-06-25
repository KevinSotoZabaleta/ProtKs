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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.protks.ProtKs.R;

public class Material extends Fragment {

    private MaterialViewModel mViewModel;

    EditText nombre, precio;
    Button guardar;

    public static Material newInstance() {
        return new Material();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.material_fragment, container, false);

        nombre = (EditText)view.findViewById(R.id.et_nombre);
        precio = (EditText)view.findViewById(R.id.et_precio);
        guardar = (Button)view.findViewById(R.id.bt_guardar);

        return view;
    }
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
