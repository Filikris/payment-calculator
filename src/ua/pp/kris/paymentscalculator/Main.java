package ua.pp.kris.paymentscalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class Payment {
    private int amount;
    private String purpose;

    public Payment(int amount, String purpose) {
        this.amount = amount;
        this.purpose = purpose;
    }

    public int getAmount() {
        return amount;
    }

    public String getPurpose() {
        return purpose;
    }

    @Override
    public String toString() {
        return amount + " " + purpose;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        List<Payment> payments = new ArrayList<>();
        System.out.println("Please, enter information about your expenses and income:");
        System.out.println("('-' expenses/'+' income. Type END to exit)");

        while (true) {
            if (in.hasNextInt()) {
                Integer amount = in.nextInt();
                String purpose = in.nextLine();
                payments.add(new Payment(amount,purpose));
            } else {
                String line = in.nextLine();
                if (line.equalsIgnoreCase("end")) break;
                else System.out.println("Warning: Unexpected line");
            }
        }

        int plus = payments.stream().filter(o -> o.getAmount() > 0).mapToInt(o -> o.getAmount()).sum();

        int minus = payments.stream().filter(o -> o.getAmount() < 0).mapToInt(o -> o.getAmount()).sum();

        int all = plus + minus;

        Map<String, Integer> amountsByPurposePlus
                = payments.stream().filter(o -> o.getAmount() > 0).collect(Collectors.groupingBy(Payment::getPurpose,
                Collectors.summingInt(Payment::getAmount)));

        Map<String, Integer> amountsByPurposeMinus
                = payments.stream().filter(o -> o.getAmount() < 0).collect(Collectors.groupingBy(Payment::getPurpose,
                Collectors.summingInt(Payment::getAmount)));

        System.out.println("Total income: " + all);

        System.out.println("The expenses are " + Math.abs(minus) + " of which: ");
        amountsByPurposeMinus.forEach((key, value) -> System.out.println(key + ":" +Math.abs(value)));

        System.out.println("The income are " + plus + " of which: ");
        amountsByPurposePlus.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
