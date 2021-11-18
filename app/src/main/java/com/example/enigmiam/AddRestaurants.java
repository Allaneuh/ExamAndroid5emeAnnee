package com.example.enigmiam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.feishengwu.datetimepicker.DateTimePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddRestaurants extends AppCompatActivity {
    EditText editNom, editCritique;
    Slider sliderDeco, sliderService;
    Button btnDate;
    FloatingActionButton fab;
    TextView txtDateFeedback;
    Context ctx;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurants);
        intent = getIntent();
        Critique critique= new Critique();
        editNom= findViewById(R.id.editNom);
        editCritique = findViewById(R.id.editCritique);
        btnDate  = findViewById(R.id.btnPickDate);
        sliderDeco = findViewById(R.id.sliderDeco);
        sliderService = findViewById(R.id.sliderService);
        fab = findViewById(R.id.btnConfirmAddingRestaurant);
        txtDateFeedback = findViewById(R.id.txtDateTime);
        changeButtonVisibility(true);
        sliderService.setValue(5);
        sliderDeco.setValue(5);
        Critique crit = (Critique) intent.getSerializableExtra("critique");
        if(crit!= null){
            fillFields(crit);
        }
        ctx = this;
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeButtonVisibility(false);
                pickDate();
            }


        });
        txtDateFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
        fab = findViewById(R.id.btnConfirmAddingRestaurant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MyDataBaseHelper db = new MyDataBaseHelper(ctx);
            String nomResto, date, noteDeco, noteService, descriptionCritique;
            nomResto=editNom.getText().toString();
            date = txtDateFeedback.getText().toString();
            noteDeco = Float.toString(sliderDeco.getValue());
            noteService = Float.toString(sliderService.getValue());
            descriptionCritique= editCritique.getText().toString();
                if(crit!= null){
                    crit.setNomResto(nomResto);
                    crit.setDateEtHeure(date);
                    crit.setNoteService(noteService);
                    crit.setNoteDeco(noteDeco);
                    crit.setCritique(descriptionCritique);
                    db.updateCritique(crit);
                    Intent intent =new Intent(ctx,MainActivity.class);
                    ctx.startActivity(intent);
                }
                else{
            if (nomResto.length()> 0 & date.length()> 0 & noteDeco.length()> 0 & noteService.length()> 0 & descriptionCritique.length()> 0 ){
                critique.setNomResto(nomResto);
                critique.setDateEtHeure(date);
                critique.setNoteService(noteService);
                critique.setNoteDeco(noteDeco);
                critique.setCritique(descriptionCritique);
                db.addCritique(critique);
                Intent intentListe = new Intent(ctx, MainActivity.class);
                ctx.startActivity(intentListe);
            }else {
                Toast.makeText(ctx, "Vérifiez votre saisie, tout les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            }
            }
            }

        });

        editNom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editCritique.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }
    public void changeButtonVisibility(boolean visibility){
        if(visibility){
            btnDate.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);

        }else{
            btnDate.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void pickDate() {
        DateTimePickerDialog selectDialog = new DateTimePickerDialog();
        selectDialog.setDefaultDateTime(Calendar.getInstance()); //Set default selected date time.
        selectDialog.setOnResultsListener(new DateTimePickerDialog.OnResultsListener() {
            @Override
            public void onSuccess(Calendar date) {
                changeButtonVisibility(true);
                Toast.makeText(AddRestaurants.this, date.getTime().toString(), Toast.LENGTH_SHORT).show();
                txtDateFeedback.setText(date.getTime().toString());
                btnDate.setVisibility(View.GONE);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) //Set the transition animation when dialog opened.
                .add(R.id.addRestaurantRoot, selectDialog) //Show dialog. "R.id.root_view" should be replaced by the ID of activity's root view.
                .addToBackStack(null) //Add dialog to back stack.
                .commit();
    }

    public void fillFields(Critique crit){
        editNom= findViewById(R.id.editNom);
        editCritique = findViewById(R.id.editCritique);
        sliderDeco = findViewById(R.id.sliderDeco);
        sliderService = findViewById(R.id.sliderService);
        txtDateFeedback = findViewById(R.id.txtDateTime);
        int position = crit.noteDeco.indexOf(".");
        String noteDeco= crit.noteDeco.substring(0,position);
        position=crit.noteService.indexOf('.');
        String noteService= crit.noteService.substring(0,position);
        btnDate = findViewById(R.id.btnPickDate);
        btnDate.setVisibility(View.GONE);
        sliderService.setValue(Integer.parseInt(noteService));
        sliderDeco.setValue(Integer.parseInt(noteDeco));
        editNom.setText(crit.nomResto);
        editCritique.setText(crit.critique);
        txtDateFeedback.setText(crit.dateEtHeure);

    }
}
