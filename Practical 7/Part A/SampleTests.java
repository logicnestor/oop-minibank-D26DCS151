public class SampleTests {

    @Run
    public void testAddition() {
        System.out.println("testAddition passed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("testSubtraction passed");
    }

    public void helperMethod() {
        System.out.println("This should NOT run.");
    }

    @Run
    public void testMessage() {
        System.out.println("testMessage passed");
    }

    public void normalMethod() {
        System.out.println("This should NOT run.");
    }
}