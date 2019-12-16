package com.saurav;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

public class PdfReader {
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PdfReader window = new PdfReader();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public PdfReader() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 550;
        int height = 500;
        frame.setBounds(dim.width/2 - width/2, dim.height/2 - height/2, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnConfig = new JMenu("Config");
        menuBar.add(mnConfig);

        JMenuItem mntmDbConnection = new JMenuItem("DB connection");
        mntmDbConnection.addActionListener(e -> {
            JFrame frame1 = new JFrame();
            Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
            int width1 = 350;
            int height1 = 200;
            int x = dim.width/2 - width1/2;
            int y = dim1.height/2 - height1/2;
            frame1.setBounds(x, y, width1, height1);
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.getContentPane().setBackground(Color.white);
            frame1.getContentPane().setLayout(null);

            JLabel mysqlHost = new JLabel("Mysql Host");
            mysqlHost.setBounds(10, 10, 110, 25);
            frame1.getContentPane().add(mysqlHost);

            JTextField mysqlHostInput = new JTextField();
            mysqlHostInput.setText(ConfigDb.getValue("mysqlHost"));
            mysqlHostInput.setBounds(120, 10, 220, 25);
            frame1.getContentPane().add(mysqlHostInput);

            JLabel userName = new JLabel("Mysql User");
            userName.setBounds(10, 40, 110, 25);
            frame1.getContentPane().add(userName);

            JTextField userNameInput = new JTextField();
            userNameInput.setText(ConfigDb.getValue("userName"));
            userNameInput.setBounds(120, 40, 220, 25);
            frame1.getContentPane().add(userNameInput);

            JLabel password = new JLabel("Mysql Password");
            password.setBounds(10, 70, 110, 25);
            frame1.getContentPane().add(password);

            JTextField passwordInput = new JTextField();
            passwordInput.setText(ConfigDb.getValue("password"));
            passwordInput.setBounds(120, 70, 220, 25);
            frame1.getContentPane().add(passwordInput);

            JLabel database = new JLabel("Mysql Database");
            database.setBounds(10, 100, 110, 25);
            frame1.getContentPane().add(database);

            JTextField databaseInput = new JTextField();
            databaseInput.setText(ConfigDb.getValue("database"));
            databaseInput.setBounds(120, 100, 220, 25);
            frame1.getContentPane().add(databaseInput);

            JButton btnSubmit = new JButton("Update");
            btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSubmit.setBackground(new Color(51,122,183));
            btnSubmit.setOpaque(true);
            btnSubmit.setBorderPainted(false);
            btnSubmit.setForeground(Color.white);
            btnSubmit.setBounds(247, 140, 89, 23);
            frame1.getContentPane().add(btnSubmit);

            JLabel connectionStatus = new JLabel();
            connectionStatus.setBounds(10, 140, 150, 25);
            frame1.getContentPane().add(connectionStatus);

            btnSubmit.addActionListener(onUpdate -> {
                ConfigDb.updateKeyValue("mysqlHost", mysqlHostInput.getText());
                ConfigDb.updateKeyValue("database", databaseInput.getText());
                ConfigDb.updateKeyValue("userName", userNameInput.getText());
                ConfigDb.updateKeyValue("password", passwordInput.getText());
                boolean connection = DbService.createConnection();
                if(connection) {
                    connectionStatus.setForeground(new Color(23,121,23));
                    connectionStatus.setText("Connection Stabilised");
                } else {
                    connectionStatus.setForeground(Color.red);
                    connectionStatus.setText("Connection Failed");
                }
            });

            frame1.setVisible(true);
        });
        mnConfig.add(mntmDbConnection);

        JLabel cclLogo = new JLabel(new ImageIcon(this.getClass().getResource("/image/cil-logo.jpg")));
        cclLogo.setBounds(150, 15, 105, 145);
        frame.getContentPane().add(cclLogo);


        JLabel lblName = new JLabel("Pdf Reader");
        lblName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        lblName.setBounds(270, 80, 230, 25);
        frame.getContentPane().add(lblName);

        String fileChooseText = " -- please choose file -- ";
        String toBeUploaded = "-- to be uploaded --";

        JTextField fileChoosen = new JTextField();
        fileChoosen.setText(fileChooseText);
        fileChoosen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        fileChoosen.setBounds(65, 287, 300, 23);
        fileChoosen.setEditable(false);
        frame.getContentPane().add(fileChoosen);

        JButton fileChoose = new JButton("Browse");
        fileChoose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fileChoose.setBackground(new Color(23,121,23));
        fileChoose.setForeground(Color.white);
        fileChoose.setOpaque(true);
        fileChoose.setBorderPainted(false);
        fileChoose.setBounds(365, 287, 89, 23);
        frame.getContentPane().add(fileChoose);

        fileChoose.addActionListener(e -> {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                // set the label to the path of the selected file
                fileChoosen.setText(j.getSelectedFile().getAbsolutePath());
                System.out.println(j.getSelectedFile().getAbsolutePath());
            }
        });

        JButton btnSubmit = new JButton("Upload");
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setBackground(new Color(51,122,183));
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBounds(65, 387, 89, 23);
        frame.getContentPane().add(btnSubmit);

        JButton btnReset = new JButton("Reset");
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setBackground(new Color(40, 40, 40));
        btnReset.setOpaque(true);
        btnReset.setBorderPainted(false);
        btnReset.setForeground(Color.white);
        btnReset.setBounds(165, 387, 89, 23);
        frame.getContentPane().add(btnReset);

        JLabel uploadStatus = new JLabel(toBeUploaded);
        uploadStatus.setBounds(65, 427, 500, 23);
        frame.getContentPane().add(uploadStatus);

        btnSubmit.addActionListener(event -> {
            if(fileChooseText.equals(fileChoosen.getText())) {
                JOptionPane.showMessageDialog(null, "Please choose a file to upload");
            } else {
                try{
                    int uploadedCount = PdfParser.parsePdf(fileChoosen.getText());
                    uploadStatus.setForeground(new Color(23,121,23));
                    uploadStatus.setText(uploadedCount + " data uploaded successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                    uploadStatus.setForeground(Color.red);
                    uploadStatus.setText("Error uploading file");
                }
            }
        });

        btnReset.addActionListener(event -> {
            fileChoosen.setText(fileChooseText);
            uploadStatus.setText(toBeUploaded);
        });

    }
}
