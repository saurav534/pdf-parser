package com.saurav;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PdfParser {
    static void parsePdf(String path, String password, JLabel uploadStatus) {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(path), password);
            if(document.isEncrypted()){
                document.setAllSecurityToBeRemoved(true);
            }
            document.removePage(0);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            PDFTextStripper tStripper = new PDFTextStripper();

            String pdfFileInText = tStripper.getText(document);
            String lines[] = pdfFileInText.split("\\r?\\n");
            handlePdfLines(lines, uploadStatus);
        } catch (InvalidPasswordException e){
            e.printStackTrace();
            decorateStatusLabel(uploadStatus, DataProcessingState.WRONG_PASSWORD, 0);
        } catch (Exception e) {
            e.printStackTrace();
            decorateStatusLabel(uploadStatus, DataProcessingState.DEFAULT_ERROR, 0);
        } finally {
            if(document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handlePdfLines(String[] lines, JLabel uploadStatus) {
        AtomicReference<DataProcessingState> dps = new AtomicReference<>(DataProcessingState.DEFAULT_ERROR);
        List<WagonDetail> wagonDetails = new ArrayList<>();
        int count = 0;
        ArrayList<String[]> dataList = new ArrayList<>();
        try {
            for (String line : lines) {
                if (!Character.isDigit(line.charAt(0))) continue;
                String[] s = line.split(" ");
                dataList.add(s);
                count++;
                WagonDetail wagonDetail = new WagonDetail();
                wagonDetail.serialNumber = Integer.parseInt(s[0]);
                wagonDetail.own = s[1];
                wagonDetail.type = s[2];
                wagonDetail.wagonNumber = s[3];
                wagonDetail.cc = Integer.parseInt(s[4]);
                wagonDetail.tare = Float.parseFloat(s[5]);
                wagonDetail.numberOfArt = Integer.parseInt(s[6]);
                wagonDetail.cmdtCode = Integer.parseInt(s[7]);
                wagonDetail.grossWt = Float.parseFloat(s[8]);
                wagonDetail.dipWt = Float.parseFloat(s[9]);
                wagonDetail.actlWt = Float.parseFloat(s[10]);
                wagonDetail.prmCc = Integer.parseInt(s[11]);
                wagonDetail.total = Float.parseFloat(s[12]);
                wagonDetail.normRate = Float.parseFloat(s[13]);
                wagonDetail.punRate = Float.parseFloat(s[14]);
                wagonDetail.chblWt = Float.parseFloat(s[15]);
                wagonDetails.add(wagonDetail);
            }
        } catch (Exception e){
            dps.set(DataProcessingState.PARSING_FAILED);
        }

        JFrame tableFrame = new JFrame();
        String[] columnNames = {"Serial Number", "Own", "Type", "Wagon Number",
                "CC", "Tare", "Number of Art", "CMDT Code", "Gross Wt", "Dip Wt",
                "ACTL Wt", "PRM CC", "Total", "Norm Rate", "Pun Rate", "CHBL Wt"};

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JTable jTable = new JTable(dataList.toArray(new String[0][]), columnNames);
        jTable.setBounds(0, 0, dim.width, dim.height - 130);
        jTable.setBorder(new MatteBorder(0, 0, 0, 0, Color.black));
        jTable.setEnabled(false);

        Font f = new Font("Arial", Font.BOLD, 12);
        JTableHeader header = jTable.getTableHeader();
        header.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
        header.setFont(f);

        JScrollPane sp = new JScrollPane(jTable);

        JButton btnUpload = new JButton("Upload");
        btnUpload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpload.setBackground(new Color(51,122,183));
        btnUpload.setOpaque(true);
        btnUpload.setBorderPainted(false);
        btnUpload.setForeground(Color.white);
        btnUpload.setBounds(dim.width/2 - 40, dim.height-100, 89, 23);

        sp.setBounds(0, 0, dim.width, dim.height - 130);
        tableFrame.getContentPane().add(sp);
        tableFrame.getContentPane().add(btnUpload);

        tableFrame.setBounds(0,0, dim.width, dim.height);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.getContentPane().setBackground(Color.white);
        tableFrame.getContentPane().setLayout(null);
        tableFrame.setVisible(true);

        dps.set(DataProcessingState.PARSED);

        int finalCount = count;
        tableFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                decorateStatusLabel(uploadStatus, dps.get(), finalCount);
            }
        });

        btnUpload.addActionListener(e -> {
            try {
                DbService.insertPdfData(wagonDetails);
                dps.set(DataProcessingState.UPLOADED_TO_DB);
                tableFrame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                dps.set(DataProcessingState.DB_UPLOAD_ERROR);
            }
        });
    }

    private static void decorateStatusLabel(JLabel uploadStatus, DataProcessingState dps, int count){
        uploadStatus.setText((dps.equals(DataProcessingState.UPLOADED_TO_DB)? count : "") + dps.explanation);
        uploadStatus.setForeground(dps.isSuccess);
    }
}
