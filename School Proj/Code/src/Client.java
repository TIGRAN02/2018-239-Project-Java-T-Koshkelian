import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int serverPort = 6666;
        String address = "127.0.0.1";
        try {
            InetAddress ipAddress = InetAddress.getByName(address);

            Socket socket = new Socket(ipAddress, serverPort);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;
            Reader r0 = new Reader("Выберите тест: ", 1);
            r0.setVisible(true);
            r0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            r0.setSize(200,200);
            r0.setResizable(false);
            r0.setLocationRelativeTo(null);
            while (!r0.getT()) {
                Thread.sleep(10);
            }
            Scanner fin = null;
            try {
                fin = new Scanner(new File("test" + r0.getF1() + ".txt"));
                int k = 0;
                Reader r = new Reader("");
                while (fin.hasNext()) {
                    r.setL1(fin.nextLine());
                    r.setVisible(true);
                    r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    r.setSize(200,200);
                    r.setResizable(false);
                    r.setLocationRelativeTo(null);
                    out.writeUTF(fin.nextLine());
                    out.flush();
                    line = in.readUTF();
                    while (!r.getT()) {
                        Thread.sleep(10);
                    }
                    if(line.equals(r.getF1())) {
                        r.setL2("ПРАВИЛЬНО!");
                        k++;
                    } else {
                        r.setL2("НЕПРАВИЛЬНО!");
                    }
                    r.setT(false);
                    Thread.sleep(2000);
                    r.setF1("");
                    r.setL2("");
                }
                Reader r1 = new Reader("Ваша оценка: " + k, 0);
                r1.setVisible(true);
                r1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                r1.setSize(200,200);
                r1.setResizable(false);
                r1.setLocationRelativeTo(null);
            }catch (FileNotFoundException e) {
                System.out.println("Не найден файл" + e);
            }
            finally {
                if (fin != null) {
                    fin.close();
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
