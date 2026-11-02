import java.awt.*;
/*
 * ini adalah kode atau file untuk membuat objek pipa, saya menggunakan berbagai atribut dasar seperti posisi y, posisi x,
 * menggunakan lebar dan tinggi pipa lalu ada untuk gambar nya juga, untuk velocityX adalah kecepatan pipa bergerak secara horizontal ke kiri
 * untuk passsed itu harusnya untuk mengecek apakah pipa tersebut sudah telewati atau belum tetapi saya tidak mengguanaknnya.
 * Semua astribut disini juga memiliki getter dan setter nya sendiri*/

public class Pipe {
    // Data satu pipa. Pipa atas dan bawah adalah dua objek Pipe terpisah.
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    boolean passed;

    // Konstruktor kosong untuk membuat pipa yang diisi belakangan lewat setter.
    public Pipe(){}

    // Konstruktor lengkap. velocityX dan passed diberi nilai awal tetap karena gerak pipa
    // diatur oleh Logic (pipeVelocityX), bukan oleh atribut di sini.
    public Pipe(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityX = 0;
        this.passed = false;
    }

    // Getter dan setter posisi; posX diubah tiap frame oleh Logic.move().
    public int getPosX(){
        return this.posX;
    }
    public void setPosX(int posX){
        this.posX = posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setPosY(int posY){
        this.posY = posY;
    }
    // Getter dan setter ukuran, dipakai untuk menggambar dan mengecek tabrakan.
    public int getWidth(){
        return this.width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return this.height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    // Getter dan setter gambar pipa.
    public Image getImage(){
        return this.image;
    }
    public void setImage(Image image){
        this.image = image;
    }
    // Getter dan setter kecepatan dan status lewat; saat ini belum dibaca oleh bagian lain program.
    public int getVelocityX(){
        return this.velocityX;
    }
    public void setVelocityX(int velocityX){
        this.velocityX = velocityX;
    }
    public boolean getPassed(){
        return this.passed;
    }
    public void setPassed(boolean passed){
        this.passed = passed;
    }
}