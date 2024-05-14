public class MainGeneric {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Generic1<String> objGeneric1 = new Generic1<String>("ini latihan");

            String input = objGeneric1.getData();
            System.out.println(input);
            objGeneric1.displayData();
    }
}
