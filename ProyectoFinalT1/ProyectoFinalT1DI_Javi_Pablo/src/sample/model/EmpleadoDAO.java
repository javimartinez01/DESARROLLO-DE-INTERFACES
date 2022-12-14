package sample.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

//Empleado DAO (Data Access Object)
public class EmpleadoDAO {

    //Busqueda de un empleado mediante su ID
    public static Empleado searchEmployee (String empId) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM empleados WHERE employee_id="+empId;

        try {
            ResultSet rsEmp = DBUtil.dbExecuteQuery(selectStmt);
            Empleado employee = getEmployeeFromResultSet(rsEmp);
            return employee;
        } catch (SQLException e) {
            System.out.println("!ERROR¡ al buscar un empleado con su ID" + empId + " del empleado: " + e);
            throw e;
        }
    }

    //Se usa el conjunto de resultados de la DB como parámetro y establecer los atributos del objeto EMPLEADO y los devuelva.
    private static Empleado getEmployeeFromResultSet(ResultSet rs) throws SQLException
    {
        Empleado emp = null;
        if (rs.next()) {
            emp = new Empleado();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
        }
        return emp;
    }


    public static ObservableList<Empleado> searchEmployees () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM empleado";

        try {
            ResultSet rsEmps = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Empleado> empList = getEmployeeList(rsEmps);
            return empList;
        } catch (SQLException e) {
            System.out.println("!ERROR¡ la operación select SQL ha fallado: " + e);
            throw e;
        }
    }

    //Select * desde la operación de los Empleados
    private static ObservableList<Empleado> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Empleado> empList = FXCollections.observableArrayList();

        while (rs.next()) {
            Empleado emp = new Empleado();
            emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
            emp.setFirstName(rs.getString("FIRST_NAME"));
            emp.setLastName(rs.getString("LAST_NAME"));
            emp.setEmail(rs.getString("EMAIL"));
            emp.setPhoneNumber(rs.getString("PHONE_NUMBER"));
            emp.setHireDate(rs.getDate("HIRE_DATE"));
            emp.setJobId(rs.getString("JOB_ID"));
            emp.setSalary(rs.getInt("SALARY"));
            emp.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
            emp.setManagerId(rs.getInt("MANAGER_ID"));
            emp.setDepartmantId(rs.getInt("DEPARTMENT_ID"));
            empList.add(emp);
        }
        return empList;
    }

    // Actualizar el correo electronico a traves del update
    public static void updateEmpEmail (String empId, String empEmail) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "BEGIN\n" +
                        "   UPDATE employees\n" +
                        "      SET EMAIL = '" + empEmail + "'\n" +
                        "    WHERE EMPLOYEE_ID = " + empId + ";\n" +
                        "   COMMIT;\n" +
                        "END;";

        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("!ERROR¡ ocurrido en la operación de actiualización: " + e);
            throw e;
        }
    }

    //Eliminamos un empleado a traves del delete
    public static void deleteEmpWithId (String empId) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "BEGIN\n" +
                        "   DELETE FROM employees\n" +
                        "         WHERE employee_id ="+ empId +";\n" +
                        "   COMMIT;\n" +
                        "END;";

        //Execute UPDATE operation
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("!ERROR¡ ocurrido en la operación de eliminación: " + e);
            throw e;
        }
    }

    //Añadimos un empleado a traves del insert
    public static void insertEmp (String name, String lastname, String email) throws SQLException, ClassNotFoundException {
        String updateStmt =
                "BEGIN\n" +
                        "INSERT INTO employees\n" +
                        "(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, HIRE_DATE, JOB_ID)\n" +
                        "VALUES\n" +
                        "(sequence_employee.nextval, '"+name+"', '"+lastname+"','"+email+"', SYSDATE, 'IT_PROG');\n" +
                        "END;";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("!ERROR¡ ocurrido en la operación de insertar un nuevo empleado: " + e);
            throw e;
        }
    }
}
