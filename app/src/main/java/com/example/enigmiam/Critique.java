package com.example.enigmiam;

import java.io.Serializable;

public class Critique implements Serializable {
    int idCritique;
    String nomResto;
    String dateEtHeure;
    String noteDeco;
    String noteService;
    String critique;
public  Critique(){

}
    public int getIdCritique() {
        return idCritique;
    }

    public void setIdCritique(int idCritique) {
        this.idCritique = idCritique;
    }



    public String getNomResto() {
        return nomResto;
    }

    public void setNomResto(String nomResto) {
        this.nomResto = nomResto;
    }

    public String getDateEtHeure() {
        return dateEtHeure;
    }

    public void setDateEtHeure(String dateEtHeure) {
        this.dateEtHeure = dateEtHeure;
    }

    public String getNoteDeco() {
        return noteDeco;
    }

    public void setNoteDeco(String noteDeco) {
        this.noteDeco = noteDeco;
    }

    public String getNoteService() {
        return noteService;
    }

    public void setNoteService(String noteService) {
        this.noteService = noteService;
    }

    public String getCritique() {
        return critique;
    }

    public void setCritique(String critique) {
        this.critique = critique;
    }

    public Critique(String nomResto, String dateEtHeure, String noteDeco, String noteService, String critique){
    this.nomResto = nomResto;
    this.dateEtHeure = dateEtHeure;
    this.noteDeco = noteDeco;
    this.noteService = noteService;
    this.critique= critique;

    }
}
