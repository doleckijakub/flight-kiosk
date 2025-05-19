package util;

import registry.*;
import model.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;

public class Printer {
    private static final String TMP_DIR = "/tmp";
    
    public static String printPlaneTicket(Flight flight, Flyer flyer, String seat) throws Exception {
        String baseFilename = "ticket_" + flight.getNumber() + "_" + flyer.getPassportNumber().hashCode() + "_" + System.currentTimeMillis();
        String latexFile = Paths.get(TMP_DIR, baseFilename + ".tex").toString();
        String pdfFile = Paths.get(TMP_DIR, baseFilename + ".pdf").toString();
        String latexContent = generateLatexTicket(flight, flyer, seat);
        compileLatexToPdf(latexFile, pdfFile, latexContent);
        return pdfFile;
    }

    public static String printReceipt(String[] items, double[] prices) throws Exception {
        if (items == null || prices == null || items.length != prices.length) {
            throw new IllegalArgumentException("Items and prices arrays must be non-null and of equal length");
        }

        String baseFilename = "receipt_" + System.currentTimeMillis();
        String latexFile = Paths.get(TMP_DIR, baseFilename + ".tex").toString();
        String pdfFile = Paths.get(TMP_DIR, baseFilename + ".pdf").toString();
        String latexContent = generateReceiptLatex(items, prices);
        compileLatexToPdf(latexFile, pdfFile, latexContent);
        return pdfFile;
    }

    private static void compileLatexToPdf(String latexFile, String pdfFile, String latexContent) throws Exception {
        Files.write(Paths.get(latexFile), latexContent.getBytes());
        
        ProcessBuilder pb = new ProcessBuilder(
            "pdflatex",
            "-interaction=nonstopmode",
            "-output-directory=" + TMP_DIR,
            latexFile
        );
        
        pb.redirectErrorStream(true);
        Process process = pb.start();
        
        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            throw new Exception(
                "LaTeX compilation failed with exit code " + exitCode
            );
        }
        
        if (!Files.exists(Paths.get(pdfFile))) {
            throw new IOException("PDF output file was not created: " + pdfFile);
        }
    }
    
    private static String generateLatexTicket(Flight flight, Flyer flyer, String seat) {
        return "\\documentclass{article}\n" +
               "\\usepackage[utf8]{inputenc}\n" +
               "\\usepackage{graphicx}\n" +
               "\\usepackage{geometry}\n" +
               "\\geometry{a4paper, margin=1cm}\n" +
               "\\begin{document}\n" +
               "\\thispagestyle{empty}\n" +
               "\\begin{center}\n" +
               "\\Large\\textbf{BOARDING PASS}\n" +
               "\\vspace{0.5cm}\n" +
               "\\end{center}\n" +
               "\\begin{tabular}{ll}\n" +
               "\\textbf{Flight Number:} & " + escapeLatex(flight.getNumber()) + " \\\\\n" +
               "\\textbf{Passenger:} & " + escapeLatex(flyer.getFullName()) + " [" + escapeLatex(flyer.getPassportNumber()) + "] \\\\\n" +
               "\\textbf{Seat:} & " + escapeLatex(seat) + " \\\\\n" +
               "\\textbf{Plane:} & " + escapeLatex(flight.getPlane().getName()) + 
               " (" + escapeLatex(flight.getPlane().getNumber()) + ") \\\\\n" +
               "\\textbf{Destination:} & " + escapeLatex(flight.getDestination().getName() + " [" + flight.getDestination().getCode() + "]") + " \\\\\n" +
               "\\textbf{Departure Time:} & " + escapeLatex(flight.getDepartureTime()) + " \\\\\n" +
               "\\end{tabular}\n" +
               "\\end{document}";
    }

    private static String generateReceiptLatex(String[] items, double[] prices) {
        StringBuilder latexBuilder = new StringBuilder();
        
        latexBuilder.append("\\documentclass{article}\n")
                   .append("\\usepackage[utf8]{inputenc}\n")
                   .append("\\usepackage{geometry}\n")
                   .append("\\geometry{a4paper, margin=1cm}\n")
                   .append("\\usepackage{longtable}\n")
                   .append("\\begin{document}\n")
                   .append("\\thispagestyle{empty}\n")
                   .append("\\begin{center}\n")
                   .append("\\Large\\textbf{SALES RECEIPT}\n")
                   .append("\\\\ \\vspace{0.5cm}\n")
                   .append("\\small ").append(escapeLatex(
                       LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                   .append("\\end{center}\n")
                   .append("\\vspace{0.5cm}\n")
                   .append("\\begin{longtable}{|l|r|}\n")
                   .append("\\hline\n")
                   .append("\\textbf{Item} & \\textbf{Price} \\\\ \\hline\n");
        
        double total = 0.0;
        for (int i = 0; i < items.length; i++) {
            latexBuilder.append(escapeLatex(items[i]))
                       .append(" &")
                       .append(String.format("%.2f PLN", prices[i]))
                       .append(" \\\\ \\hline\n");
            total += prices[i];
        }
        
        latexBuilder.append("\\multicolumn{1}{|r|}{\\textbf{Total:}} &")
                   .append(String.format("%.2f PLN", total))
                   .append(" \\\\ \\hline\n")
                   .append("\\end{longtable}\n")
                   .append("\\end{document}");
        
        return latexBuilder.toString();
    }

    private static String escapeLatex(String input) {
        if (input == null) return "";
        return input.replace("&", "\\&")
                   .replace("_", "\\_")
                   .replace("#", "\\#")
                   .replace("$", "\\$")
                   .replace("%", "\\%");
    }

}