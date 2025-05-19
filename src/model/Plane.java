package model;

import util.ANSI;
import java.util.*;

public final class Plane {
    public final String number;
    public final String name;
    public final int rows1;  // rows before middle exit
    public final int rows2;  // rows after middle exit
    public final int colGroups;  // seat groups in a row
    public final int cols;  // seats in a group

    public Plane(String number, String name, int rows1, int rows2, int colGroups, int cols) {
        this.number = Objects.requireNonNull(number, "Plane number cannot be null");
        this.name = Objects.requireNonNull(name, "Plane name cannot be null");
        this.rows1 = rows1;
        this.rows2 = rows2;
        this.colGroups = colGroups;
        this.cols = cols;
    }

    public String getNumber() { return number; }
    public String getName() { return name; }

    public int totalRows() {
        return rows1 + rows2;
    }

    public int totalSeats() {
        return totalRows() * colGroups * cols;
    }

    public void draw(Set<String> takenSeats) {
        System.out.println("\n" + ANSI.YELLOW + name + " (" + number + ")" + ANSI.RESET);
        drawSeatMap(takenSeats);
    }

    private void drawSeatMap(Set<String> takenSeats) {
        int totalRows = rows1 + rows2;
        int totalCols = colGroups * cols;

        System.out.print("    ");
        for (int col = 0; col < totalCols; col++) {
            char colChar = (char) ('A' + col);
            System.out.print(" " + colChar + " ");
            if (col == cols * colGroups / 2 - 1) System.out.print("  ");
        }
        System.out.println();

        for (int row = 1; row <= totalRows; row++) {
            if (row == 1) {
                System.out.println(ANSI.YELLOW + "    ==== FRONT EXIT ====" + ANSI.RESET);
            } else if (row == rows1 + 1) {
                System.out.println(ANSI.YELLOW + "    === MIDDLE EXIT ===" + ANSI.RESET);
            }

            System.out.printf("%2d |", row);

            for (int col = 0; col < totalCols; col++) {
                char colChar = (char) ('A' + col);
                String seat = String.format("%c%02d", colChar, row);

                if (col == cols * colGroups / 2) {
                    System.out.print("  ");
                }

                if (takenSeats.contains(seat)) {
                    System.out.print(ANSI.RED + "[X]" + ANSI.RESET);
                } else {
                    System.out.print(ANSI.CYAN + "[" + colChar + "]" + ANSI.RESET);
                }
            }
            System.out.println();
        }
        System.out.println(ANSI.YELLOW + "    ==== REAR EXIT ====" + ANSI.RESET);
    }

}