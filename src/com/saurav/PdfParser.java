package com.saurav;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class PdfParser {
    static int parsePdf(String path) throws IOException, SQLException {
        try (PDDocument document = PDDocument.load(new File(path))) {

            if (!document.isEncrypted()) {
                document.removePage(0);
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                String lines[] = pdfFileInText.split("\\r?\\n");
                return handlePdfLines(lines);
            }
        }
        return 0;
    }

    private static int handlePdfLines(String[] lines) throws SQLException {
        List<WagonDetail> wagonDetails = new ArrayList<>();
        int count = 0;
        for (String line : lines) {
            if(!Character.isDigit(line.charAt(0))) continue;
            count++;
            String[] s = line.split(" ");
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
        DbService.insertPdfData(wagonDetails);
        return count;
    }
}
