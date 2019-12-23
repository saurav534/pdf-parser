package com.saurav;

import javax.swing.*;
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
        int width = 570;
        int height = 520;
        frame.setBounds(dim.width/2 - width/2, dim.height/2 - height/2, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JLabel pwdLabel = new JLabel("PDF password");
        pwdLabel.setForeground(new Color(61,132,203));
        pwdLabel.setBounds(75, 337, 95, 23);
        frame.getContentPane().add(pwdLabel);

        JTextField usePwd = new JTextField(ConfigDb.getValue("docPassword"));
        usePwd.setBounds(170, 337, 150, 23);
        frame.getContentPane().add(usePwd);


        JMenu mnConfig = new JMenu("Config");
        mnConfig.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuBar.add(mnConfig);



        JMenuItem mntmDbConnection = new JMenuItem("DB connection");
        mnConfig.add(mntmDbConnection);
        mntmDbConnection.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mntmDbConnection.addActionListener(e -> {
            JFrame frame1 = new JFrame();
            frame1.setTitle("Mysql Configuration");
            Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
            int width1 = 350;
            int height1 = 215;
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

        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        mnConfig.add(s);

        JMenuItem mntmDocPassword = new JMenuItem("Password");
        mnConfig.add(mntmDocPassword);
        mntmDocPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mntmDocPassword.addActionListener(e -> {
            JFrame pwdFrame = new JFrame();
            pwdFrame.setTitle("Document Password");
            Dimension dim1 = Toolkit.getDefaultToolkit().getScreenSize();
            int width1 = 350;
            int height1 = 165;
            int x = dim.width/2 - width1/2;
            int y = dim1.height/2 - height1/2;
            pwdFrame.setBounds(x, y, width1, height1);
            pwdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pwdFrame.getContentPane().setBackground(Color.white);
            pwdFrame.getContentPane().setLayout(null);

            JLabel mysqlHost = new JLabel("Default Password");
            mysqlHost.setBounds(10, 40, 110, 25);
            pwdFrame.getContentPane().add(mysqlHost);

            JTextField mysqlHostInput = new JTextField();
            mysqlHostInput.setText(ConfigDb.getValue("docPassword"));
            mysqlHostInput.setBounds(120, 40, 220, 25);
            pwdFrame.getContentPane().add(mysqlHostInput);

            JLabel pwdStatus = new JLabel();
            pwdStatus.setBounds(10, 100, 150, 25);

            pwdFrame.getContentPane().add(pwdStatus);

            JButton btnSubmit = new JButton("Update");
            btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSubmit.setBackground(new Color(51,122,183));
            btnSubmit.setOpaque(true);
            btnSubmit.setBorderPainted(false);
            btnSubmit.setForeground(Color.white);
            btnSubmit.setBounds(247, 100, 89, 23);
            pwdFrame.getContentPane().add(btnSubmit);

            btnSubmit.addActionListener(event -> {
                pwdStatus.setForeground(Color.black);
                pwdStatus.setText("Updating...");
                new Thread(() -> {
                    ConfigDb.updateKeyValue("docPassword", mysqlHostInput.getText());
                    pwdStatus.setForeground(new Color(23,121,23));
                    pwdStatus.setText("Password Updated");
                    usePwd.setText(mysqlHostInput.getText());
                }).start();
            });

            pwdFrame.setVisible(true);
        });

        JLabel cclLogo = new JLabel(new ImageIcon(this.getClass().getResource("/image/cil-logo.jpg")));
        cclLogo.setBounds(160, 15, 105, 145);
        frame.getContentPane().add(cclLogo);


        JLabel lblName = new JLabel("Pdf Reader");
        lblName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        lblName.setBounds(280, 80, 230, 25);
        frame.getContentPane().add(lblName);

        String fileChooseText = " -- please choose file -- ";
        String toBeUploaded = "-- to be uploaded --";

        JTextField fileChoosen = new JTextField();
        fileChoosen.setText(fileChooseText);
        fileChoosen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        fileChoosen.setBounds(75, 287, 300, 23);
        fileChoosen.setEditable(false);
        frame.getContentPane().add(fileChoosen);

        JButton fileChoose = new JButton("Browse");
        fileChoose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fileChoose.setBackground(new Color(23,121,23));
        fileChoose.setForeground(Color.white);
        fileChoose.setOpaque(true);
        fileChoose.setBorderPainted(false);
        fileChoose.setBounds(375, 287, 89, 23);
        frame.getContentPane().add(fileChoose);

        fileChoose.addActionListener(e -> {
            JFileChooser j = new JFileChooser(System.getProperty("user.home")+"\\Desktop");
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                // set the label to the path of the selected file
                fileChoosen.setText(j.getSelectedFile().getAbsolutePath());
            }
        });

        JButton btnSubmit = new JButton("Upload");
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setBackground(new Color(51,122,183));
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBounds(75, 387, 89, 23);
        frame.getContentPane().add(btnSubmit);

        JButton btnReset = new JButton("Reset");
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setBackground(new Color(40, 40, 40));
        btnReset.setOpaque(true);
        btnReset.setBorderPainted(false);
        btnReset.setForeground(Color.white);
        btnReset.setBounds(175, 387, 89, 23);
        frame.getContentPane().add(btnReset);

        JLabel uploadStatus = new JLabel(toBeUploaded);
        uploadStatus.setBounds(75, 427, 500, 23);
        frame.getContentPane().add(uploadStatus);

        btnSubmit.addActionListener(event -> {
            if(fileChooseText.equals(fileChoosen.getText())) {
                JOptionPane.showMessageDialog(null, "Please choose a file to upload");
            } else {
                uploadStatus.setText("Processing ...");
                Thread thread = new Thread(() -> {
                    PdfParser.parsePdf(fileChoosen.getText(), usePwd.getText(), uploadStatus);
                });
                thread.start();
            }
        });

        btnReset.addActionListener(event -> {
            fileChoosen.setText(fileChooseText);
            uploadStatus.setText(toBeUploaded);
            uploadStatus.setForeground(Color.black);
        });

    }
}
