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
