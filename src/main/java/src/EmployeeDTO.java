package src;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement(name = "employee")
@JsonbPropertyOrder(value = {"id", "name", "birth", "departmentId" ,"salary", "bonus", "email", "phone"})
public class EmployeeDTO {
    private int id;
    private String name;
    private Date birth;
    private int departmentId;
    private Double salary;
    private Double bonus;
    private String email;
    private String phone;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int id, String name, Date birth, int departmentId, Double salary, Double bonus, String email, String phone) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.departmentId = departmentId;
        this.salary = salary;
        this.bonus = bonus;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirth() {
        return birth;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    
}