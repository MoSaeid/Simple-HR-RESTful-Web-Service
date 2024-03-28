package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("departments")
public class DepartmentResource {
   private final String DB_URL = "jdbc:mysql://localhost:3306/hrdb?useSSL=false";
   private final String DB_USER = "root";
   private final String DB_PASSWORD = "";

   @GET
   @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response getAllDepartments() {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM departments");

         List<DepartmentDTO> departmentList = new ArrayList<>();
         while (rs.next()) {
            int id = rs.getInt("id");
            String depName = rs.getString("dep_name");
            String phone = rs.getString("phone");
            int manager = rs.getInt("manager");

            DepartmentDTO department = new DepartmentDTO(id, depName, phone, manager);
            departmentList.add(department);
         }

         conn.close();
         return Response.ok(departmentList).build();
      } catch (SQLException e) {
         return Response.serverError().entity("Error retrieving departments").build();
      }
   }

   @GET
   @Path("{id}")
   @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response getDepartmentById(@PathParam("id") int id) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM departments WHERE id = ?");
         stmt.setInt(1, id);
         ResultSet rs = stmt.executeQuery();

         if (rs.next()) {
            String depName = rs.getString("dep_name");
            String phone = rs.getString("phone");
            int manager = rs.getInt("manager");

            DepartmentDTO department = new DepartmentDTO(id, depName, phone, manager);
            conn.close();
            return Response.ok(department).build();
         } else {
            conn.close();
            return Response.status(Response.Status.NOT_FOUND).entity("Department not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error retrieving department").build();
      }
   }

   @POST
   @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response addDepartment(DepartmentDTO department) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO departments (id, dep_name, phone, manager) VALUES (?, ?, ?, ?)");
         stmt.setInt(1, department.getId());
         stmt.setString(2, department.getDepName());
         stmt.setString(3, department.getPhone());
         stmt.setInt(4, department.getManager());
         stmt.executeUpdate();

         conn.close();
         return Response.status(Response.Status.CREATED).entity("Department added successfully").build();
      } catch (SQLException e) {
         return Response.serverError().entity("Error adding department").build();
      }
   }
   
   
   @PUT
   @Path("{id}")
   @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
   public Response updateDepartment(@PathParam("id") int id, DepartmentDTO updatedDepartment) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("UPDATE departments SET dep_name = ?, phone = ?, manager = ? WHERE id = ?");
         stmt.setString(1, updatedDepartment.getDepName());
         stmt.setString(2, updatedDepartment.getPhone());
         stmt.setInt(3, updatedDepartment.getManager());
         stmt.setInt(4, id);
         int rowsAffected = stmt.executeUpdate();

         conn.close();

         if (rowsAffected > 0) {
            return Response.ok().entity("Department updated successfully").build();
         } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Department not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error updating department").build();
      }
   }

   @DELETE
   @Path("{id}")
   public Response deleteDepartment(@PathParam("id") int id) {
      try {
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM departments WHERE id = ?");
         stmt.setInt(1, id);
         int rowsAffected = stmt.executeUpdate();

         conn.close();

         if (rowsAffected > 0) {
            return Response.ok().entity("Department deleted successfully").build();
         } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Department not found").build();
         }
      } catch (SQLException e) {
         return Response.serverError().entity("Error deleting department").build();
      }
   }
}