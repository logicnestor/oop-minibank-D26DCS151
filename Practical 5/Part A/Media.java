public abstract class Media {

    private String title;

    public Media(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // Each media type calculates its own fee
    public abstract double lateFee(int lateDays);

    @Override
    public String toString() {
        return title;
    }
}