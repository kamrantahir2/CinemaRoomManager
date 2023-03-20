package cinema;

import java.util.Scanner;

public class Cinema {
    static int rows = 0;
    static int seatsInRow = 0;
    static int seatsSold = 0;
    static int totalSeats = 0;
    static int income = 0;
    static int sum = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int income = 0;
        int frontHalf;
        int backHalf;
        int selectedRow = 0;
        int selectedSeat = 0;
        int costOfSeat = 0;

        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInRow = scanner.nextInt();

        String[][] seatsArray = createGrid(rows, seatsInRow);

        while (true) {
            boolean finished = false;
            System.out.println("1. Show the seats \n2. Buy a ticket \n3. Statistics \n0. Exit");

            int input = scanner.nextInt();

            switch (input) {
                case 0:
                    finished = true;
                    break;
                case 1:
                    printArray(seatsArray);
                    break;
                case 2:
                    System.out.println("Enter a row number:");
                    selectedRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row: ");
                    selectedSeat = scanner.nextInt();

                    if (selectedRow < 0 || selectedRow > rows || selectedSeat < 0 || selectedSeat > seatsInRow) {
                        System.out.println("Wrong Input");
                        continue;
                    } else {
                        boolean booked = bookSeat(seatsArray, selectedRow, selectedSeat);
                        if (!booked) {
                            while (!booked) {
                                System.out.println("Enter a row number:");
                                selectedRow = scanner.nextInt();
                                System.out.println("Enter a seat number in that row: ");
                                selectedSeat = scanner.nextInt();
                                booked = bookSeat(seatsArray, selectedRow, selectedSeat);

                            }
                        } else {
                            continue;
                        }
                    }
                    break;
                case 3:
                    showStatistics();
                    break;
                default:
                    System.out.println("Wrong input!");
            }

            if (finished) {
                break;
            }
        }
    }

    // **** Main Ends Here ****
    // ============================================================================

    // **** Methods start here ****

    public static void showStatistics() {
        float percentage = 0;
        if (Cinema.seatsSold != 0) {
            percentage = (float) Cinema.seatsSold / Cinema.totalSeats * 100;
        }

        System.out.println("Number of purchased tickets: " + Cinema.seatsSold);
        String str = String.format("Percentage: %.2f", percentage);
        System.out.println(str+"%");
        System.out.println("Current income: $" + Cinema.sum);
        System.out.println("Total income: $" + potentialIncome());
    }

    public static String[][] createGrid(int rows, int seatsInRow) {
        String[][] seatsArray = new String[rows + 1][seatsInRow + 1];
        // This first for loop only loops through the first array
        seatsArray[0][0] = " ";
        for (int i = 1; i < seatsArray[0].length; i++) {
            seatsArray[0][i] = String.valueOf(i);
        }

        for (int i = 1 ; i < seatsArray.length; i++) {
            for (int j = 0; j < seatsArray[i].length; j++) {
                if (j == 0) {
                    seatsArray[i][j] = String.valueOf(i);
                    continue;
                }
                seatsArray[i][j] = "S";
            }
        }
        return seatsArray;
    }

    public static boolean bookSeat(String[][] seatsArray, int selectedRow, int selectedSeat) {

        Cinema.totalSeats = rows * seatsInRow;
        int costOfSeat = 0;
        int frontHalf = 0;
        int backHalf = 0;
        boolean booked = false;

        if (Cinema.totalSeats <= 60) {
            Cinema.totalSeats = rows * seatsInRow;
            Cinema.income += (Cinema.totalSeats * 10);
            costOfSeat = 10;
        } else {
            frontHalf =  (int) Math.floor(rows / 2);
            backHalf = rows - frontHalf;
            int frontHalfSeats = frontHalf * seatsInRow;
            int remainingSeats = backHalf * seatsInRow;
            Cinema.income += (frontHalfSeats * 10);
            Cinema.income += (remainingSeats * 8);
            if (selectedRow <= frontHalf){
                costOfSeat = 10;
            } else{
                costOfSeat = 8;
            }
        }

        if (seatsArray[selectedRow][selectedSeat].equalsIgnoreCase("s")) {
            seatsArray[selectedRow][selectedSeat] = "B";
            Cinema.seatsSold ++;
            Cinema.sum += costOfSeat;
            System.out.println("Ticket price: $" + costOfSeat);
            booked = true;
        } else {
            System.out.println("That ticket has already been purchased!");
        }
        return booked;
    }

    public static int potentialIncome() {
        int sum = 0;

        Cinema.totalSeats = rows * seatsInRow;
        int frontHalf = 0;
        int backHalf = 0;

        if (Cinema.totalSeats <= 60) {
            sum = Cinema.totalSeats * 10;
        } else {
            frontHalf =  (int) Math.floor(rows / 2);
            backHalf = rows - frontHalf;
            int eightSum = (backHalf * Cinema.seatsInRow) * 8;
            int tenSum = (frontHalf * Cinema.seatsInRow) * 10;

            sum = eightSum + tenSum;

        }

        return sum;
    }

//    ===============================================================

    public static void printArray(String[][] seatsArray) {
        System.out.println("Cinema:");
        for (int i = 0; i < seatsArray.length; i++) {
            String row = "";
            for (int j = 0; j < seatsArray[i].length; j++) {
                row += seatsArray[i][j];
                row += " ";

            }
            System.out.println(row);
        }

    }

}