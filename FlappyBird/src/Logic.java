import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.ArrayList;

// Otak game: menyimpan state, menggerakkan objek, mengecek tabrakan dan skor, serta menerima input keyboard.
public class Logic implements ActionListener, KeyListener {
    // Ukuran layar, dipakai sebagai acuan posisi awal burung dan pipa.
    private int frameWidth = 360;
    private int frameHeight = 640;

    // Posisi dan ukuran awal burung: di tengah layar agar punya ruang untuk naik dan jatuh.
    private int playerStartPosX = frameWidth/2;
    private int playerStartPosY = frameHeight/2;
    private int playerWidth = 34;
    private int playerHeight = 24;

    // Posisi dan ukuran awal pipa: X di tepi kanan layar sehingga pipa masuk dari luar pandangan.
    private int pipeStartPosX = frameWidth;
    private int pipeStartPosY = 0;
    private int pipeWidth = 64;
    private int pipeHeight = 512;


    // Timer: gameLoop menjalankan satu frame tiap tick, pipesCooldown mengatur jeda munculnya pipa baru.
    private Timer gameLoop;
    private Timer pipesCooldown;
    // Percepatan jatuh burung per frame.
    private int gravity = 1;

    // Negatif karena pipa bergerak ke kiri menuju burung.
    private int pipeVelocityX = -2;

    // Objek game. Pipa disimpan berpasangan: indeks genap pipa atas, indeks ganjil pipa bawah.
    private View view;
    private Image birdImage;
    private Player player;

    private Image lowerPipeImage;
    private Image upperPipeImage;
    private ArrayList<Pipe> pipes;

    private Clip backgroundClip;

    // Konstruktor: memuat gambar, membuat burung, lalu menyalakan kedua timer.
    public Logic(){
        birdImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/upperPipe.png")).getImage();
        pipes = new ArrayList<>();
        // Pipa baru dibuat tiap 1500 ms agar jarak antar pasangan pipa konstan.
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });
        pipesCooldown.start();
        // 1000/60 ms per tick menghasilkan sekitar 60 frame per detik.
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

        if(backgroundClip != null && backgroundClip.isRunning()){
            backgroundClip.stop();
        }
        backgroundClip = loadSound("BGM.wav", true);
        if(backgroundClip != null){
            backgroundClip.start();
        }
    }
    // View tidak diberikan lewat konstruktor karena View sendiri membutuhkan Logic saat dibuat.
    public void setView(View view){
        this.view = view;
    }
    // Getter agar View bisa mengambil burung dan pipa untuk digambar.
    public Player getPlayer(){
        return player;
    }
    public ArrayList<Pipe> getPipes(){
        return pipes;
    }
    // Membuat sepasang pipa (atas lalu bawah) dengan celah di tinggi acak.
    public void placePipes(){
        // Y pipa atas negatif agar hanya bagian bawahnya yang terlihat; rentang acaknya membuat
        // posisi celah bervariasi. Celah dibuat seperempat tinggi layar.
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 2) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);
        // Pipa bawah dimulai tepat di bawah pipa atas ditambah celah.
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }
    // Game over jika burung jatuh melewati batas bawah layar. Batas atas tidak dicek di sini
    // karena posisi Y sudah dijepit di move().
    public void nabrakTembok(){
        if(player.getPosY() >= frameHeight ){
            // Timer dihentikan lebih dulu supaya dialog tidak muncul berulang di tiap frame.
            backgroundClip.stop();
            gameLoop.stop();
            JOptionPane.showMessageDialog(null, "GAME OVER\ntekan R untuk mengulang permainan");
        }
    }

    // Mengecek tabrakan burung dengan tiap pipa sekaligus menghitung skor.
    public void cekTiang(){
        int i = 0;
        // Skor dihitung ulang dari nol di tiap frame berdasarkan jumlah pipa yang sudah dilewati,
        // jadi tidak perlu menyimpan skor antar frame.
        int j = 0;
        Boolean nabrak = false;
        // Kondisi nabrak ada di while agar perulangan berhenti tanpa break.
        while(i < pipes.size() && nabrak == false){
            Pipe pipa = pipes.get(i);
            // Bagian tabrakan: angka 12 dan 8 memperkecil hitbox burung dari ukuran gambarnya.
            // Pengecekan vertikal hanya memakai sisi bawah burung.
            if(player.getPosX() + player.getWidth() - 12 >= pipa.getPosX() && player.getPosX() - 12 + player.getWidth() <= pipa.getPosX() + pipa.getWidth() && player.getPosY() + player.getHeight() - 8 >= pipa.getPosY() && player.getPosY() + player.getHeight() - 8 <= pipa.getPosY() + pipa.getHeight()){
                gameLoop.stop();
                nabrak = true;
                backgroundClip.stop();
            }
            else{
                i++;
            }
            // Bagian skor: burung melewati pipa saat X-nya sudah di kanan tepi kanan pipa. i sudah naik
            // di cabang else, jadi i genap berarti pipa yang baru dicek berindeks ganjil (pipa bawah),
            // sehingga satu pasangan pipa menambah skor satu kali.
            if(player.getPosX() > pipa.getPosX() + pipa.getWidth() && i % 2 == 0) {
                j++;
                view.updateTitle(j);
            }
        }
        // Dialog ditampilkan setelah perulangan agar tidak muncul di tengah pengecekan.
        if(nabrak == true){
            JOptionPane.showMessageDialog(null, "GAME OVER\ntekan R untuk mengulang permainan");
        }
    }


    // Menggerakkan burung dan pipa untuk satu frame.
    public void move() {
        // Burung: gravitasi menambah kecepatan dulu, baru posisi berubah, sehingga jatuhnya makin cepat.
        // Posisi dijepit di batas atas layar agar burung tidak keluar dari pandangan.
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // Pipa: semua pipa digeser ke kiri dengan kecepatan yang sama.
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);
        }
    }

    public Clip loadSound(String filName, boolean loop){
        try{
            InputStream audioSrc = getClass().getResourceAsStream("FlappyBirdAssets/Audio/" + filName);
            if(audioSrc == null){
                return null;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            if(loop){
                clip.loop(clip.LOOP_CONTINUOUSLY);
            }
            return clip;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Dipanggil gameLoop tiap tick. Urutannya penting: gerak dulu, baru cek tabrakan pada posisi terbaru.
    @Override
    public void actionPerformed(ActionEvent e){
        move();
        nabrakTembok();
        cekTiang();
        // view bisa masih null jika timer berjalan sebelum setView dipanggil.
        if(view != null){
            view.repaint();
        }
    }

    // Bagian input keyboard: keyTyped dan keyReleased tidak dipakai.
    @Override
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            // Kecepatan diganti (bukan ditambah) agar tiap lompatan setinggi sama.
            player.setVelocityY(-10);
        }
        else if(e.getKeyCode() == KeyEvent.VK_R){
            // Bagian ulang permainan: timer dinyalakan lagi, pipa dihapus, lalu burung dan skor
            // dikembalikan ke kondisi awal.
            gameLoop.start();
            pipes.clear();
            player.setVelocityX(0);
            player.setVelocityY(0);
            player.setPosY(playerStartPosY);
            player.setPosX(playerStartPosX);
            view.updateTitle(0 );
            backgroundClip.setFramePosition(0);
            backgroundClip.start();
        }
    }
    public void keyReleased(KeyEvent e){}
}