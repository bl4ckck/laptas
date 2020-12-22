package com.kelompoklaptas.laptas;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Laporan {
    private String title;
    private String description;
    private String status;
    private Timestamp date;
    private String image;
    private String id_berwenang;
    private String id_pelapor;

    public  Laporan(){

    }

    public Laporan(String title, String description, String status, Timestamp date, String image, String id_berwenang, String id_pelapor) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.image = image;
        this.id_berwenang = id_berwenang;
        this.id_pelapor = id_pelapor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_berwenang() {
        return id_berwenang;
    }

    public void setId_berwenang(String id_berwenang) {
        this.id_berwenang = id_berwenang;
    }

    public String getId_pelapor() {
        return id_pelapor;
    }

    public void setId_pelapor(String id_pelapor) {
        this.id_pelapor = id_pelapor;
    }
}
