package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("employees")
public class EmployeeResource {
   private final String DB_URL = "jdbc:mysql://localhost:3306/hrdb?useSSL=false";
   private final String DB_USER = "root";
   private final String DB_PASSWORD = "";

   @GET
   @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response getAllEmployees() {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

         List<EmployeeDTO> employeeList = new ArrayList<>();
         while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Date birth = rs.getDate("birth");
            int departmentId = rs.getInt("department_id");
            double salary = rs.getDouble("salary");
            double bonus = rs.getDouble("bonus");
            String email = rs.getString("email");
            String phone = rs.getString("phone");

            EmployeeDTO employee = new EmployeeDTO(id, name, birth, departmentId, salary, bonus, email, phone);
            employeeList.add(employee);
         }

         conn.close();
         return Response.ok(employeeList).build();
      } catch (SQLException e) {
         return Response.serverError().entity("Error retrieving employees").build();
      }
   }

   @GET
   @Path("{id}")
   @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response getEmployeeById(@PathParam("id") int id) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
         stmt.setInt(1, id);
         ResultSet rs = stmt.executeQuery();

         if (rs.next()) {
            String name = rs.getString("name");
            Date birth = rs.getDate("birth");
            int departmentId = rs.getInt("department_id");
            double salary = rs.getDouble("salary");
            double bonus = rs.getDouble("bonus");
            String email = rs.getString("email");
            String phone = rs.getString("phone");

            EmployeeDTO employee = new EmployeeDTO(id, name, birth, departmentId, salary, bonus, email, phone);
            conn.close();
            return Response.ok(employee).build();
         } else {
            conn.close();
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error retrieving employee").build();
      }
   }

   @POST
   @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response addEmployee(EmployeeDTO employee) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO employees (id, name, birth, department_id, salary, bonus, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
         stmt.setInt(1, employee.getId());
         stmt.setString(2, employee.getName());
         stmt.setDate(3, employee.getBirth());
         stmt.setInt(4, employee.getDepartmentId());
         stmt.setDouble(5, employee.getSalary());
         stmt.setDouble(6, employee.getBonus());
         stmt.setString(7, employee.getEmail());
         stmt.setString(8, employee.getPhone());
         stmt.executeUpdate();

         conn.close();
         return Response.status(Response.Status.CREATED).entity("Employee added successfully").build();
      } catch (SQLException e) {
         return Response.serverError().entity("Error adding employee").build();
      }
   }
   
   
   @PUT
   @Path("{id}")
   @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response updateEmployee(@PathParam("id") int id, EmployeeDTO updatedEmployee) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET name = ?, birth = ?, department_id = ?, salary = ?, bonus = ?, email = ?, phone = ? WHERE id = ?");
         stmt.setString(1, updatedEmployee.getName());
         stmt.setDate(2, updatedEmployee.getBirth());
         stmt.setInt(3, updatedEmployee.getDepartmentId());
         stmt.setDouble(4, updatedEmployee.getSalary());
         stmt.setDouble(5, updatedEmployee.getBonus());
         stmt.setString(6, updatedEmployee.getEmail());
         stmt.setString(7, updatedEmployee.getPhone());
         stmt.setInt(8, id);
         int rowsAffected = stmt.executeUpdate();

         conn.close();

         if (rowsAffected > 0) {
            return Response.ok().entity("Employee updated successfully").build();
         } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error updating employee").build();
      }
   }

   @DELETE
   @Path("{id}")
   public Response deleteEmployee(@PathParam("id") int id) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE id = ?");
         stmt.setInt(1, id);
         int rowsAffected = stmt.executeUpdate();

         conn.close();

         if (rowsAffected > 0) {
            return Response.ok().entity("Employee deleted successfully").build();
         } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error deleting employee").build();
      }
   }
}