package demo.mayank.myallbooks.models;

public class CountryModel {

    private String name;

    public CountryModel(String name) {
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
