    package ph.activelearning;

    import java.sql.*;
    /**
     * @author jonathanseanestaya
     */
    public final class Database {
        private String driver, dbUrl, dbUsername, dbPassword;
        private Connection link;
        private boolean isLoaded = false;
        public static final int DRIVER = 0;
        public static final int URL = 1;
        public static final int USERNAME = 2;
        public static final int PASSWORD = 3;

        public Database() {

        }

        public Database(String driver, String dbUrl, String dbUsername, String dbPassword) {
            this.driver = driver;
            this.dbUrl = dbUrl;
            this.dbUsername = dbUsername;
            this.dbPassword = dbPassword;
        }



        public boolean set(int typeToSet, String parameter) {
            switch(typeToSet) {
                case 0: this.driver = parameter;
                    break;
                case 1: this.dbUrl = parameter;
                    break;
                case 2: this.dbUsername = parameter;
                    break;
                case 3: this.dbPassword = parameter;
                    break;
                default: return false;
            }

            return true;
        }

        public Connection getConnection() {
            try {
                if(!isLoaded) {
                    Class.forName(driver);
                    isLoaded = true;
                    System.out.println("Driver is Loaded");
                }

                if(link == null) {
                    link = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                    System.out.println("Connection Established");
                }
            }
            catch(ClassNotFoundException | SQLException e) {
                System.err.println(e);
            }

            return link;
        }

        public boolean closeConnection() {
            if(link == null)
                return false;

            try {
                link.close();
                link = null;
                System.out.println("Connection Terminated");
            }
            catch(SQLException sqle) {
                System.err.println(sqle);
            }

            return true;
        }
    }