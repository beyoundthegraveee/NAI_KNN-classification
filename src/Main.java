import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Podaj K: ");
        int k = Integer.parseInt(bufferedReader.readLine());
        KNN knn = new KNN(k);
        System.out.println("[1] własny wektor \n" +
                "[2] dokonać klasyfikacji\n"+
                "[3] wyjście");
        String ans = bufferedReader.readLine();
        switch (ans) {
            case "1":
                System.out.println("Wprowadź swój wektor z separatorem [,]:");
                String str = bufferedReader.readLine();
                double[] userVector = Arrays.stream(str.split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                knn.classifyCustomVector(userVector);
                break;
            case "2":
                knn.classifyKNN();
                break;
            case "3":
                System.exit(1);
            default:
                break;

        }
    }
}