public class Magazine extends Media {

    public Magazine(String title) {
        super(title);
    }

    @Override
    public double lateFee(int lateDays) {
        return lateDays * 1.0;
    }
}