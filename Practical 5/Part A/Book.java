public class Book extends Media {

    public Book(String title) {
        super(title);
    }

    @Override
    public double lateFee(int lateDays) {
        return lateDays * 2.0;
    }
}