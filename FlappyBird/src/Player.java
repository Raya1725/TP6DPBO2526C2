import java.awt.*;

// Menyimpan posisi, ukuran, gambar, dan kecepatan burung. Logika geraknya ada di Logic, bukan di sini.
public class Player{
    // Posisi dan ukuran dipakai View untuk menggambar dan Logic untuk mengecek tabrakan.
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    // Hanya velocityY yang dipakai Logic karena burung bergerak vertikal saja.
    private int velocityY = -0;
    private int velocityX = 0;

    // Konstruktor kosong untuk membuat pemain yang diisi belakangan lewat setter.
    public Player(){

    }

    // Konstruktor lengkap dengan posisi, ukuran, dan gambar awal.
    public Player(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    // Getter dan setter posisi horizontal.
    public void setPosX(int posX){
        this.posX = posX;
    }

    public int getPosX(){
        return this.posX;
    }

    // Getter dan setter posisi vertikal; diubah tiap frame oleh Logic.move() akibat gravitasi dan lompatan.
    public void setPosY(int posY){
        this.posY = posY;
    }

    public int getPosY(){
        return this.posY;
    }

    // Getter dan setter ukuran, dipakai untuk menggambar dan menghitung hitbox.
    public void setWidth(int width){
        this.width = width;
    }

    public int getWidth(){
        return this.width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getHeight(){
        return this.height;
    }

    // Getter dan setter gambar burung.
    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return this.image;
    }

    // Getter dan setter kecepatan vertikal; negatif berarti naik, positif berarti jatuh.
    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY){
        this.velocityY = velocityY;
    }

    // Getter dan setter kecepatan horizontal; belum dipakai untuk menggerakkan burung.
    public int getVelocityX(){
        return this.velocityX;
    }

    public void setVelocityX(int velocityX){
        this.velocityX = velocityX;
    }
}