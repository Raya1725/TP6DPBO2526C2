import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;

/*
 * untuk program atau file ini adalah tempat untuk mengatur semua tampilan dana game saya
 * untuk class saya saya buat dengan nama view yang menjadi anak dari class JPanel*/

public class View extends JPanel {
    // Ukuran area gambar, sama dengan ukuran layar yang diasumsikan Logic.
    private int width = 360;
    private int height = 640;
    private Logic logic;
    // Gambar latar dimuat sekali di constructor agar tidak dibaca ulang dari disk di tiap frame.
    private Image backgroundImage;

    // Konstanta posisi label skor agar tidak ada angka layout yang tersebar di kode.
    private static final int TITLE_ROW = 0;
    private static final int TITLE_COLUMN = 0;
    private static final int TITLE_COLUMN_SPAN = 3;

    // Label skor, rata kanan agar angka berada di sisi kanan atas layar.
    private final JLabel labelTitle = new JLabel("0", SwingConstants.RIGHT);


    // Menyiapkan panel: ukuran, warna langit, penerimaan keyboard, dan label skor.
    public View(Logic logic){
        this.logic =  logic;
        setPreferredSize(new Dimension(width, height));
        backgroundImage = new ImageIcon(getClass().getResource("FlappyBirdAssets/background.png")).getImage();

        // Panel harus focusable agar KeyListener dari Logic bisa menerima tombol keyboard.
        setFocusable(true);
        addKeyListener(logic);
        addTitle();
    }

    // Dipanggil Swing tiap repaint: super membersihkan layar lebih dulu, lalu objek game digambar ulang.
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    // Memasang label skor. Constraint ini hanya berlaku jika layout panel GridBagLayout;
    // dengan layout bawaan JPanel (FlowLayout) constraint diabaikan.
    public void addTitle() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = TITLE_COLUMN;
        constraints.gridy = TITLE_ROW;
        constraints.gridwidth = TITLE_COLUMN_SPAN;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        add(labelTitle, constraints);
    }


    // Mengganti angka skor di label; dipanggil Logic saat skor berubah atau game diulang.
    public void updateTitle(int newTitle) {
        String text = String.valueOf(newTitle);
        labelTitle.setText(text);
    }

    // Menggambar burung lalu semua pipa. Dicek null karena View bisa digambar sebelum data siap.
    public void draw(Graphics g){
        // Latar digambar pertama agar burung dan pipa tampil di atasnya.
        g.drawImage(backgroundImage, 0, 0, width, height, null);
        // Bagian burung.
        Player player = logic.getPlayer();
        if(player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        }

        // Bagian pipa: semua pipa, atas dan bawah, digambar dari posisinya masing-masing.
        ArrayList<Pipe> pipes = logic.getPipes();
        if(pipes != null){
            for(int i = 0; i < pipes.size(); i++){
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }
    }

}