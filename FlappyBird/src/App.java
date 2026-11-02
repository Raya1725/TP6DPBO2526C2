import javax.swing.*;
import java.awt.*;

public class App {
    private static final String[] MENU_OPTIONS = {"Mulai", "Keluar"};
    private static final int START_OPTION = 0;

    public static void main(String[] args){
        //ini adalah variabel untuk menyimpan nilai dari opsi menu di awal untuk memulai permainan
        //variabel ini akan menyimpan nilai 0 jika user memilih yes, dan akan menyimpan nilai 1 jika user memilih no
        int option = JOptionPane.showOptionDialog(null, "FlappyBird", "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, MENU_OPTIONS, MENU_OPTIONS[START_OPTION]);        //kondisi jika user memilih untuk memainkan permainan atau  memilih yes di start menu
        if(option == START_OPTION){
            // Bagian jendela: EXIT_ON_CLOSE agar program ikut berhenti saat jendela ditutup (timer game tidak
            // tertinggal), ukuran dikunci karena posisi pipa dan burung dihitung dengan layar 360x640.
            JFrame frame = new JFrame("FlappyBird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(360, 640);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            // Bagian penghubung: Logic dibuat dulu karena View membutuhkannya, lalu Logic diberi View
            // agar bisa memanggil repaint dan memperbarui skor.
            Logic logika = new Logic();
            View tampilan = new View(logika);
            logika.setView(tampilan);

            // Fokus diminta agar tombol keyboard langsung diterima oleh View.
            tampilan.requestFocus();

            // Bagian menampilkan: pack() menyesuaikan jendela dengan ukuran View, lalu jendela dimunculkan.
            frame.add(tampilan);
            frame.pack();
            frame.setVisible(true);
        }


    }
}