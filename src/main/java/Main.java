import java.util.List;

public class Main {
    public static void main(String[] args) {

        Arrangement arrangement = new Arrangement(12);

        arrangement.addOwner("S1", true, 0, 3, 6, 8, 10);
        arrangement.balance();
        arrangement.print();

        arrangement.addOwner("S2", true, 0, 1, 5, 7, 9);
        arrangement.balance();
        arrangement.print();

        arrangement.addOwner("S3", false, 0, 2, 5, 6, 7, 9, 10, 11);
        arrangement.balance();
        arrangement.print();

        arrangement.addOwner("S4", false, 1, 3, 4, 5, 11);
        arrangement.balance();
        arrangement.print();
    }

    private static void printTable(List<Valuable> listVal, List<Owner> listOwner, List<String> ownerNames) {
        String formatCell = "%-12s ";
        String formatEnder = "%-12s%n";

        System.out.printf(formatCell, "*blank*");
        for (int i = 0; i < listVal.size() - 1; i++) {
            System.out.printf(formatCell, "Valuable " + i);
        }
        System.out.printf(formatEnder, "Valuable " + (listOwner.size() - 1));

        for (int i = 0; i < listOwner.size(); i++) {
            Owner currentOwner = listOwner.get(i);
            System.out.printf(formatCell, ownerNames.get(i));
            for (int j = 0; j < listVal.size() - 1; j++) {
                if (listVal.get(j).getOwner().equals(currentOwner)) {
                    System.out.printf(formatCell, "X");
                } else {
                    System.out.printf(formatCell, "O");
                }
            }
            if (listVal.get(listVal.size() - 1).getOwner().equals(currentOwner)) {
                System.out.printf(formatEnder, "X");
            } else {
                System.out.printf(formatEnder, "O");
            }
        }
    }
}
