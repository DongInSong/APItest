import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnecter {

    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;

    public String[] doName = new String[18];
    public String[] doCode = new String[18];

    List<String>[] si = new ArrayList[18];

    public DBConnecter() {
        try {
            String dbDriver = "com.mysql.cj.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/";
            // Database name to access
            String dbName = "city";
            String dbUsername = "root";
            String dbPassword = "root";

            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbURL + dbName,
                    dbUsername,
                    dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert_Do() {
        DataLoader dataLoader = new DataLoader();
        try {
            for (int i = 0; i < dataLoader.doName[0].length; i++) {
                st = con.prepareStatement("insert into do(doname, code) values(?,?)");
                st.setString(1, dataLoader.doName[0][i]);
                st.setString(2, dataLoader.doName[1][i]);
                st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert_Si() {
        DataLoader dataLoader = new DataLoader();
        try {
            for (int i = 0; i < dataLoader.siNameList.length; i++) {
                for (int j = 0; j < dataLoader.siNameList[i].length; j++) {
                    st = con.prepareStatement("insert into si values(?, ?)");
                    st.setInt(1, i + 1);
                    st.setString(2, dataLoader.siNameList[i][j]);
                    st.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        DataLoader dataLoader = new DataLoader();
        try {
            for (int i = 0; i < dataLoader.doName.length; i++) {
                st = con.prepareStatement("select * from test");
                st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] read_Do() {
        int i = 0;
        try {
            st = con.prepareStatement("select doname from do");
            rs = st.executeQuery();
            while (rs.next()) {
                doName[i] = rs.getString(1);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doName;
    }

    public String[] read_DoCode() {
        int i = 0;
        try {
            st = con.prepareStatement("select code from do");
            rs = st.executeQuery();
            while (rs.next()) {
                doCode[i] = rs.getString(1);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doCode;
    }

    public List<String>[] read_Si() {
        for (int i = 0; i < 18; i++) {
            si[i] = new ArrayList<String>();
        }
        int i = 0;
        try {
            st = con.prepareStatement("select doNum, siname from si");
            rs = st.executeQuery();

            while (rs.next()) {
                int doNum = rs.getInt(1);
                String siName = rs.getString(2);
                if (i == doNum - 1) {
                    si[i].add(siName);
                } else {
                    i++;
                    si[i].add(siName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return si;
    }
}
