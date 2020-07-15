package exercise7;

public class TestClass {

    public TestClass() {
    }

    @BeforeSuite
    public void runBSMethod(){
        System.out.println("BeforeSuite executed");
    }

//    @BeforeSuite
//    public void runAnotherBSMethod(){
//        System.out.println("BeforeSuite executed");
//    }

    @Test (priority = 5)
    public void runTest5Method(){
        System.out.println("Test#5 executed");
    }

    @Test (priority = 7)
    public void runTest7Method(){
        System.out.println("Test#7 executed");
    }

    @Test
    public void runTestMethod(){
        System.out.println("First Test executed");
    }

    @Test (priority = 5)
    public void runAnotherTest5Method(){
        System.out.println("Next Test#5 executed");
    }

    public void breaking(){
        System.out.println("Next Test#5 executed");
    }

    @AfterSuite
    public void runASMethod(){
        System.out.println("AfterSuite executed");
    }

}
