package com.saurav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

class DbService {
    private static Connection conn = null;
    static {
        createConnection();
    }

    public static boolean createConnection() {
        String dbUrl = "jdbc:mysql://" + ConfigDb.getValue("mysqlHost") + "/" + ConfigDb.getValue("database") + "?useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, ConfigDb.getValue("userName"), ConfigDb.getValue("password"));
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void insertPdfData(List<WagonDetail> wagonDetails) throws SQLException {
        String query = "insert into wagon_detail (serial_number, own, type, wagon_number, cc, tare, number_of_art, "
                + "cmdt_code, gross_wt, dip_wt, actl_wt, prm_cc, total, norm_rate, pun_rate, chbl_wt)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);

        for (WagonDetail wagonDetail : wagonDetails) {
            preparedStmt.setInt(1, wagonDetail.serialNumber);
            preparedStmt.setString(2, wagonDetail.own);
            preparedStmt.setString(3, wagonDetail.type);
            preparedStmt.setString(4, wagonDetail.wagonNumber);
            preparedStmt.setInt(5, wagonDetail.cc);
            preparedStmt.setFloat(6, wagonDetail.tare);
            preparedStmt.setInt(7, wagonDetail.numberOfArt);
            preparedStmt.setInt(8, wagonDetail.cmdtCode);
            preparedStmt.setFloat(9, wagonDetail.grossWt);
            preparedStmt.setFloat(10, wagonDetail.dipWt);
            preparedStmt.setFloat(11, wagonDetail.actlWt);
            preparedStmt.setInt(12, wagonDetail.prmCc);
            preparedStmt.setFloat(13, wagonDetail.total);
            preparedStmt.setFloat(14, wagonDetail.normRate);
            preparedStmt.setFloat(15, wagonDetail.punRate);
            preparedStmt.setFloat(16, wagonDetail.chblWt);

            preparedStmt.addBatch();
            preparedStmt.executeBatch();
        }
    }
}
