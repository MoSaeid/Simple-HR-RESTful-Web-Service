package src;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "department")
@JsonbPropertyOrder(value = {"id", "depName", "phone", "manager"})
public class DepartmentDTO {
    private int id;
    @JsonbProperty("Department-Name")
    private String depName;
    private String phone;
    private int manager;

    public DepartmentDTO() {
    }

    public DepartmentDTO(int id, String depName, String phone, int manager) {
        this.id = id;
        this.depName = depName;
        this.phone = phone;
        this.manager = manager;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public String getDepName() {
        return depName;
    }

    public String getPhone() {
        return phone;
    }

    public int getManager() {
        return manager;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }
    
    
}