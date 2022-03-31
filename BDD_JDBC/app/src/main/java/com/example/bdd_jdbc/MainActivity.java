package com.example.bdd_jdbc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ResultSet rst=null;
    public static Connection conn = null, connect;
    private EditText editPseudo, editPassword, editPassword2;
    private Spinner spinner;
    private CheckBox checkLundi, checkMardi, checkMercredi, checkJeudi, checkVendredi;
    private RadioGroup groupNiveau;
    private RadioButton radioDeb, radioInter, radioHaut;
    private Button valider;
    private String radioSelected;
    List<CheckBox> items;
    ArrayList<String> arrayCheck = null;
    ResultSet rs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog() // Enregistre un message à logcat
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath() //l'application se bloque, fonctionne à //la fin de toutes les sanctions permises
                .build());

        MysqlConnexion();


        editPseudo = (EditText) findViewById(R.id.editTextTextPersonName);
        editPassword = (EditText) findViewById(R.id.editTextTextPassword);
        editPassword2 = (EditText) findViewById(R.id.editTextTextPassword2);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<String> data = new ArrayList<String>();
        try {
            String stmt = "SELECT asso FROM spinner";
            PreparedStatement ps = conn.prepareStatement(stmt);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                data.add(rs.getString("asso"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        checkLundi = (CheckBox) findViewById(R.id.checkLundi);
        checkMardi = (CheckBox) findViewById(R.id.checkMardi);
        checkMercredi = (CheckBox) findViewById(R.id.checkMercredi);
        checkJeudi = (CheckBox) findViewById(R.id.checkJeudi);
        checkVendredi = (CheckBox) findViewById(R.id.checkVendredi);
        groupNiveau = findViewById(R.id.groupRadio);
        radioDeb = (RadioButton) findViewById(R.id.radioDeb);
        radioInter = (RadioButton) findViewById(R.id.radioInter);
        radioHaut = (RadioButton) findViewById(R.id.radioHaut);
        valider = (Button) findViewById(R.id.valider);





    }

    private Connection MysqlConnexion(){
        String jdbcURL = "jdbc:mysql://192.168.1.16:3306/android";
        String user = "connect";
        String passwd = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL,user,passwd);
            Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_LONG).show();

        } catch ( ClassNotFoundException e) {
            Toast.makeText(MainActivity.this, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        } catch ( java.sql.SQLException ex ) {
            Toast.makeText(MainActivity.this, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("error", "SQLException: " + ex.getMessage());
            Log.d("error","SQLState: " + ex.getSQLState());
            Log.d("error","VendorError: " + ex.getErrorCode());

            Log.i("I/TAG", String.valueOf(ex));
        }
        return conn;
    } // fin de MysqlConnection

    public void onClick(View view){
        if(view == valider){
            if(editPseudo.getText().toString().matches("") || editPassword.getText().toString().matches("") || editPassword2.getText().toString().matches("") || arrayCheck.isEmpty()){
                Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                return;
            }
            else if(editPassword.getText().toString().equals(editPassword2.getText().toString())) {
                try {
                    String sqlins = "INSERT INTO `association` (`pseudo`, `password`, `association`, `semaine`, `niveau`, `commentaire`) VALUES (?,?,?,?,?,?)";
                    PreparedStatement pstmins = conn.prepareStatement(sqlins);
                    pstmins.setString(1, editPseudo.getText().toString());
                    pstmins.setString(2, editPassword.getText().toString());
                    pstmins.setString(3, spinner.getSelectedItem().toString());
                    pstmins.setString(4, Arrays.toString(arrayCheck.toArray()));
                    pstmins.setString(5,radioSelected);
                    pstmins.setString(6, "commentaire");
                    pstmins.executeUpdate();

                    erase();
                    Toast.makeText(MainActivity.this, "Données enregistrées dans BDD", Toast.LENGTH_LONG).show();
                }
                catch (SQLException s) {
                    Log.i("I/TAG", String.valueOf(s));
                    Toast.makeText(MainActivity.this, "Utilisateur déjà existant", Toast.LENGTH_LONG).show();
                }
            }
            else{
                editPassword.setText("");
                editPassword2.setText("");
                Toast.makeText(MainActivity.this, "Mot de passe différents", Toast.LENGTH_LONG).show();
            }
        }

    }

    public ArrayList<String> checked(View view){
        arrayCheck = new ArrayList<String>();

        if(checkLundi.isChecked())
            arrayCheck.add(checkLundi.getText().toString());
        if(checkMardi.isChecked())
            arrayCheck.add(checkMardi.getText().toString());
        if(checkMercredi.isChecked())
            arrayCheck.add(checkMercredi.getText().toString());
        if(checkJeudi.isChecked())
            arrayCheck.add(checkJeudi.getText().toString());
        if(checkVendredi.isChecked())
            arrayCheck.add(checkVendredi.getText().toString());

        Log.i("I/TAG", Arrays.toString(arrayCheck.toArray()));

        return arrayCheck;
    }

    public String clickRadio(View view){
        RadioButton radioButton = (RadioButton) findViewById(groupNiveau.getCheckedRadioButtonId());
        radioSelected = String.valueOf(radioButton.getText());
        Log.i("I/TAG", radioSelected);

        return radioSelected;
    }
    public void erase(){
        editPseudo.setText("");
        editPassword.setText("");
        editPassword2.setText("");
        checkLundi.setChecked(false);
        checkMardi.setChecked(false);
        checkMercredi.setChecked(false);
        checkJeudi.setChecked(false);
        checkVendredi.setChecked(false);
        radioDeb.setChecked(true);
        arrayCheck.clear();
    }



}