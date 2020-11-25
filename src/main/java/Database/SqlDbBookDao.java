package Database;

import Domain.Book;
import Dao.BookDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDbBookDao implements BookDao {

    private DbConnection db;
    private Connection connection;
    private ArrayList<Book> bookList;

    public SqlDbBookDao(String dbFile) throws Exception {
        this.db = new DbConnection(dbFile);
        this.connection = db.getConnection();
    }

    public SqlDbBookDao() throws Exception {
        this("jdbc:sqlite:lukuvinkit.db");
    }

    public boolean createBook(String kirjoittaja, String nimeke,
            Integer julkaisuvuosi, Integer sivumaara) {
        return createBook(kirjoittaja, nimeke, julkaisuvuosi, sivumaara, null);
    }

    public boolean createBook(String kirjoittaja, String nimeke,
            Integer julkaisuvuosi, Integer sivumaara, String ISBN) {
        String query = "INSERT INTO books (kirjoittaja, nimeke, julkaisuvuosi, "
                + "sivumaara, ISBN) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, kirjoittaja);
            prepared.setString(2, nimeke);
            prepared.setInt(3, julkaisuvuosi);
            prepared.setInt(4, sivumaara);
            prepared.setString(5, ISBN);
            prepared.executeUpdate();

            return true;
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return false;
        }
    }

    public ArrayList<Book> getAllBooks() {
        bookList = new ArrayList<Book>();
        String query = "SELECT kirjoittaja, nimeke, "
                + "julkaisuvuosi, sivumaara, ISBN "
                + "FROM books;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()) {
                String kirjoittaja = rs.getString("kirjoittaja");
                String nimeke = rs.getString("nimeke");
                Integer julkaisuvuosi = rs.getInt("julkaisuvuosi");
                Integer sivumaara = rs.getInt("sivumaara");
                String ISBN = rs.getString("ISBN");
                Book lisattava = new Book(kirjoittaja, nimeke, julkaisuvuosi,
                        sivumaara, ISBN, null, null);

                bookList.add(lisattava);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return null;
        }
        return bookList;
    }

    public Book findByISBN(String ISBN) {
        Book book = null;
        String query = "SELECT kirjoittaja, nimeke, "
                + "julkaisuvuosi, sivumaara, ISBN "
                + "FROM books WHERE ISBN=?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, ISBN);
            ResultSet rs = prepared.executeQuery();
            book = new Book(rs.getString("kirjoittaja"), rs.getString("nimeke"),
                    rs.getInt("julkaisuvuosi"), rs.getInt("sivumaara"),
                    rs.getString("ISBN"));
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return null;
        }
        return book;
    }

    public ArrayList<Book> findByKirjoittaja(String searchTerm) {
        bookList = new ArrayList<Book>();
        String query = "SELECT kirjoittaja, nimeke, "
                + "julkaisuvuosi, sivumaara, ISBN "
                + "FROM books WHERE kirjoittaja=?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, searchTerm);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()) {
                String kirjoittaja = rs.getString("kirjoittaja");
                String nimeke = rs.getString("nimeke");
                Integer julkaisuvuosi = rs.getInt("julkaisuvuosi");
                Integer sivumaara = rs.getInt("sivumaara");
                String ISBN = rs.getString("ISBN");
                Book lisattava = new Book(kirjoittaja, nimeke, julkaisuvuosi,
                        sivumaara, ISBN, null, null);

                bookList.add(lisattava);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return null;

        }
        return bookList;
    }

    public ArrayList<Book> findByNimeke(String searchTerm) {
        bookList = new ArrayList<Book>();
        String query = "SELECT kirjoittaja, nimeke, "
                + "julkaisuvuosi, sivumaara, ISBN "
                + "FROM books WHERE kirjoittaja=?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(query);
            prepared.setString(1, searchTerm);
            ResultSet rs = prepared.executeQuery();
            while (rs.next()) {
                String kirjoittaja = rs.getString("kirjoittaja");
                String nimeke = rs.getString("nimeke");
                Integer julkaisuvuosi = rs.getInt("julkaisuvuosi");
                Integer sivumaara = rs.getInt("sivumaara");
                String ISBN = rs.getString("ISBN");
                Book lisattava = new Book(kirjoittaja, nimeke, julkaisuvuosi,
                        sivumaara, ISBN, null, null);

                bookList.add(lisattava);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());

            return null;
        }
        return bookList;
    }

}
