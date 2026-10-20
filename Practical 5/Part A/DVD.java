public class DVD extends Media {

    public DVD(String title) {
        super(title);
    }

    @Override
    public double lateFee(int lateDays) {
        return lateDays * 5.0;
    }
}