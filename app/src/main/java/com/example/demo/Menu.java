package com.example.demo;

import java.util.Map;

public class Menu {
    String jour;
    String repas;
    Map<String,String> contenu;

    public Menu(String jour, String repas, String contenu) {
        this.jour = jour;
        this.repas = repas;
    }

    public Menu() {
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getRepas() {
        return repas;
    }

    public void setRepas(String repas) {
        this.repas = repas;
    }

    public Map<String, String> getContenu() {
        return contenu;
    }

    public void setContenu(Map<String, String> contenu) {
        this.contenu = contenu;
    }

}
