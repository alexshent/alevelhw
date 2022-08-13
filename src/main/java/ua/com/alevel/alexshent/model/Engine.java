package ua.com.alevel.alexshent.model;

public class Engine {
    private int volume;
    private String brand;

    public Engine(int volume, String brand) {
        this.volume = volume;
        this.brand = brand;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
