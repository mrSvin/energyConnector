import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.List;


public class Mysql {

    private Boolean trigerCreate = false;
    String tempDate = "";

    public void createTable(String tableName) {

        if (tempDate.equals(tableName) == false) {
            Connection con = null;
            Statement stmt = null;

            try {
                DriverManager.registerDriver(new Driver());
                String mysqlUrl = "jdbc:mysql://localhost/?useUnicode=true&serverTimezone=UTC&useSSL=false";
                con = DriverManager.getConnection(mysqlUrl, "root", "root");
                stmt = con.createStatement();

                String sql = "CREATE TABLE `energy_month`.`" + tableName + "` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `vrs1` DOUBLE NULL," +
                        "  `vrs2` DOUBLE NULL," +
                        "  PRIMARY KEY (`id`));";
                stmt.executeUpdate(sql);


                //Заполняем нулями дни
                for (int i = 1; i <= 31; i++) {
                    String sql_request = "INSERT INTO `energy_month`.`" + tableName + "` (`vrs1`, `vrs2`) VALUES ('0', '0'); ";
                    stmt.executeUpdate(sql_request);
                }

                con.close();
                stmt.close();
                System.out.println("Создана таблица " + tableName);

            } catch (SQLException e) {
//            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
                System.out.println("Ошибка при создании таблицы " + tableName);
            } finally {
                try {
                    con.close();
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            tempDate = tableName;
        }

    }

    public void writeData(List data, String tableName, String coloumn) {

        try {
            DriverManager.registerDriver(new Driver());
            String mysqlUrl = "jdbc:mysql://localhost/?useUnicode=true&serverTimezone=UTC&useSSL=false";
            Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
            Statement stmt = con.createStatement();

            for (int i = 1; i < data.size() + 1; i++) {
                String sql_request = "UPDATE `energy_month`.`" + tableName + "` SET `" + coloumn + "` = '" + data.get(i - 1) + "' WHERE (`id` = '" + i + "');";
                stmt.executeUpdate(sql_request);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при записи " + coloumn);
        }

    }

}
