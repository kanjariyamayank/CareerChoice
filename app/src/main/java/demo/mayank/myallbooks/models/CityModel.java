package demo.mayank.myallbooks.models;

public class CityModel {
    private String name;

    public CityModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
