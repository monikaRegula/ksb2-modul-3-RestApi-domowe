package pl.regula.ksb2modul3restapidomowe.model;


import org.springframework.hateoas.RepresentationModel;

public class Vehicle extends RepresentationModel<Vehicle> {

    private Long id;

    private String mark;

    private String model;

    private String color;

    public Vehicle(Long id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
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
}
