package com.mycompany.zpo_lab1;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable, EmployeeDAO {

    @FXML
    public TableView<Employee> table;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn emailColumn;
    public TableColumn salaryColumn;
    public ObservableList<Employee> data = FXCollections.observableArrayList();
    public RadioButton dodawanie;
    public RadioButton modyfikacja;
    public TextField idField;
    public TextField nameField;
    public TextField emailField;
    public TextField salaryField;

    @FXML
    private void dodawanieClick(ActionEvent event) {
        if (modyfikacja.isSelected()) {
            modyfikacja.setSelected(false);
            dodawanie.setSelected(true);
            idField.setDisable(true);
        }
    }

    @FXML
    private void modyfikacjaClick(ActionEvent event) {
        if (dodawanie.isSelected()) {
            dodawanie.setSelected(false);
            modyfikacja.setSelected(true);
            idField.setDisable(false);
        }
    }

    @FXML
    private void usuwanie(ActionEvent event) {
        try {
            Employee employee = table.getSelectionModel().getSelectedItem();
            delete(employee);
            List<Employee> list = findAll();
            data.clear();
            for (Employee e : list) {
                data.add(e);
            }
            table.setItems(data);
        } catch (Exception ex) {
            System.out.println("Nie wybrano");
        }
    }

    @FXML
    private void dodajMod(ActionEvent event) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String salaryString = salaryField.getText();
            int salary = Integer.valueOf(salaryString);          
            Employee employee = new Employee();
            employee.setName(name);
            employee.setEmail(email);
            employee.setSalary(salary);
            if(modyfikacja.isSelected())
            {
                int id=Integer.valueOf(idField.getText());
                employee.setId(id);
            }
            save(employee);
            List<Employee> list = findAll();
            data.clear();
            for (Employee e : list) {
                data.add(e);
            }
            table.setItems(data);
        } catch (Exception ex) {
            System.out.println("Nie dodano!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, Integer>("id")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("email")
        );
        salaryColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, Integer>("salary")
        );
        List<Employee> list = findAll();
        data.clear();
        for (Employee e : list) {
            data.add(e);
        }
        table.setItems(data);
        System.out.println(findOne(1).get().getName());
        System.out.println(findByName("Jan Kowalski").get().getName());
        // delete(findOne(2).get());
        dodawanie.setSelected(true);
    }

    public Connection polacz() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", "root", "");
            System.out.println("Połączono z bazą");
        } catch (Exception ex) {
            System.out.println("polacz() - " + ex.toString());
        }
        return connection;
    }

    public void rozlacz(Connection connection) throws SQLException {
        connection.close();
        System.out.println("Rozłączono z bazą");
    }

    public Optional<Employee> findOne(Integer id) {
        Optional<Employee> pracownik = Optional.empty();
        try {
            Connection connection = polacz();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE id=" + id + ";");
            rs.next();
            Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getInt("salary"));
            pracownik = Optional.ofNullable(employee);
            rozlacz(connection);
        } catch (Exception ex) {
            System.out.println("findOne - " + ex.toString());
        }
        return pracownik;
    }

    public List<Employee> findAll() {
        List<Employee> lista = new ArrayList<>();
        try {
            Connection connection = polacz();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee;");
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getInt("salary"));
                lista.add(employee);
                data.add(employee);
                System.out.println(employee.getId() + " " + employee.getName() + " " + employee.getEmail() + " " + employee.getSalary());
            }
            rozlacz(connection);
        } catch (Exception ex) {
            System.out.println("findAll() -  " + ex.toString());
        }
        return lista;
    }

    public Optional<Employee> findByName(String name) {
        Optional<Employee> pracownik = Optional.empty();
        try {
            Connection connection = polacz();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE name='" + name + "';");
            rs.next();
            Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getInt("salary"));
            pracownik = Optional.ofNullable(employee);
            rozlacz(connection);
        } catch (Exception ex) {
            System.out.println("findOne - " + ex.toString());
        }
        return pracownik;
    }

    public void delete(Employee employee) {
        try {
            Connection connection = polacz();
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM employee WHERE id=" + employee.getId() + ";");
            rozlacz(connection);
        } catch (Exception ex) {
            System.out.println("delete - " + ex.toString());
        }
    }

    public void save(Employee employee) {
        try {
            Connection connection = polacz();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE id=" + employee.getId() + ";");
            int ile=0;
            while(rs.next())
            {
                ile++;
            }
            if (ile > 0) {
                stmt.execute("UPDATE employee SET name='" + employee.getName() + "' WHERE id=" + employee.getId() + ";");
                stmt.execute("UPDATE employee SET email='" + employee.getEmail() + "' WHERE id=" + employee.getId() + ";");
                stmt.execute("UPDATE employee SET salary=" + employee.getSalary() + " WHERE id=" + employee.getId() + ";");
            } else {
                System.out.println("KWEREDNA:\n"+"INSERT INTO employee (name,email,salary) VALUES ('" + employee.getName() + "','" + employee.getEmail() + "'," + employee.getSalary() + ");");
                stmt.execute("INSERT INTO employee (name,email,salary) VALUES ('" + employee.getName() + "','" + employee.getEmail() + "'," + employee.getSalary() + ");");
            }
            rozlacz(connection);
        } catch (Exception ex) {
            System.out.println("save - " + ex.toString());
        }
    }
}
