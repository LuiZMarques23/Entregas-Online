package com.example.entregasonline.fragment.empresa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.entregasonline.R;
import com.example.entregasonline.activity.empresa.EmpresaFinalizaCadastroActivity;
import com.example.entregasonline.helper.FirebaseHelper;
import com.example.entregasonline.model.Empresa;
import com.example.entregasonline.model.Login;

public class EmpresaFragment extends Fragment {

    private EditText edt_email;
    private EditText edt_senha;
    private ProgressBar progressBar;
    private Button btn_criar_conta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_empresa, container, false);

        iniciaComponentes(view);

        configCliks();

        return view;
    }

    private void configCliks(){
        btn_criar_conta.setOnClickListener(v -> validaDados());
    }
    private void validaDados(){
        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        if (!email.isEmpty()){
            if (!senha.isEmpty()){

                ocultarTeclado();

                progressBar.setVisibility(View.VISIBLE);

                Empresa empresa = new Empresa();
                empresa.setEmail(email);
                empresa.setSenha(senha);

                criarConta(empresa);

            }else {
                edt_senha.requestFocus();
                edt_senha.setError("Informe sua senha.");
            }

        }else {
            edt_email.requestFocus();
            edt_email.setError("Informe seu email.");
        }
    }

    private void criarConta(Empresa empresa){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                empresa.getEmail(), empresa.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                String id = task.getResult().getUser().getUid();

                empresa.setId(id);
                empresa.salvar();

                Login login = new Login(id, "E", false);
                login.salvar();

                requireActivity().finish();
                Intent intent = new Intent(requireActivity(), EmpresaFinalizaCadastroActivity.class);
                intent.putExtra("login", login);
                intent.putExtra("empresa", empresa);
                startActivity(intent);

            }else {
                progressBar.setVisibility(View.GONE);
                erroAutenticacao(FirebaseHelper.validaErros(task.getException().getMessage()));
            }
        });
    }
    private void erroAutenticacao(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Atenção");
        builder.setMessage(msg);
        builder.setPositiveButton("OK",((dialog, which) -> {
            dialog.dismiss();
        } ));

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void iniciaComponentes(View view){
        edt_email = view.findViewById(R.id.edt_email);
        edt_senha = view.findViewById(R.id.edt_senha);
        progressBar = view.findViewById(R.id.progressBar);
        btn_criar_conta = view.findViewById(R.id.btn_criar_conta);
    }
    private void ocultarTeclado(){
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                btn_criar_conta.getWindowToken(), 0
        );
    }
}