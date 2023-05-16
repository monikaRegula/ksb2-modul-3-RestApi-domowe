package pl.regula.ksb2modul3restapidomowe.model;


import java.util.Date;

public class Vehicle {

    private Long id;

    private String mark;

    private String model;

    private String color;

    private Date productionDate;

    public Vehicle(Long id, String mark, String model, String color, Date productionDate) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
    }

    public Vehicle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }


}
