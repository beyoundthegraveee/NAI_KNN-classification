import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class KNN {

    private String testSet = "Test-set.csv";
    private String trainSet = "Train-set.csv";
    
    boolean enable = true;

    private int K;


    public KNN(int k) {
        K = k;
    }


    public List<double[]> getDataSet(String filename) throws IOException {
        List<double[]> dataset = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = bufferedReader.readLine())!=null){
            String [] values = line.split("[^\\d.\\d]+");
            double[] data = new double[values.length];
            for (int i = 0; i < data.length; i++) {
                data[i] = Double.parseDouble(values[i]);
            }
            dataset.add(data);
        }

        return dataset;

    }

    public List<String> getTypes(String filename) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        List<String> types = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine())!=null){
            String [] parts = line.split(",");
            types.add(parts[parts.length-1]);
        }

        return types;
    }



    public double getDistance(double[] point1, double[] point2) throws IOException {
        double sum = 0.0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public List<Iris> getNeighbors(double [] testPoint, List<double[]>trainSet, List<String> types ) throws IOException {
        List<Iris> list = new ArrayList<>();
        for (int i = 0; i < trainSet.size(); i++) {
            double[] trainPoint = trainSet.get(i);
            double distance = getDistance(testPoint, trainPoint);
            list.add(new Iris(trainPoint,testPoint, distance,types.get(i)));
        }
        Collections.sort(list,Comparator.comparingDouble(Iris::getDistance));
        return list;
    }

    public void classifyKNN() throws IOException {
        List<double[]> testSet = getDataSet(this.testSet);
        List<double[]> trainSet = getDataSet(this.trainSet);
        List<String> types = getTypes(this.trainSet);
        List<String> check = new ArrayList<>();
        for (double[] testPoint : testSet) {
            List<Iris> currentNeighbors = getNeighbors(testPoint, trainSet,types);
            Map<String, Integer> typeCount = new HashMap<>();
            for (int i = 0; i < K; i++) {
                typeCount.put(currentNeighbors.get(i).getType(), typeCount.getOrDefault(currentNeighbors.get(i).getType(), 0) + 1);
            }
            int maxCount = 0;
            String maxType = null;
            for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    maxType = entry.getKey();
                }
            }
            String resultType = maxType;
            String str = Arrays.toString(testPoint)
                    .replaceAll("[\\[\\]]", "")
                    .replaceAll("\\s+", "")
                    + "," + resultType;
            check.add(str);
            System.out.println(str);
        }
        check(this.testSet, check);
    }

    public void classifyCustomVector(double[] userVector) throws IOException {
        List<double[]> trainSet = getDataSet(this.trainSet);
        List<String> types = getTypes(this.trainSet);
        List<Iris> currentNeighbors = getNeighbors(userVector, trainSet, types);
        Map<String, Integer> typeCount = new HashMap<>();
        for (int i = 0; i < K; i++) {
            typeCount.put(currentNeighbors.get(i).getType(), typeCount.getOrDefault(currentNeighbors.get(i).getType(), 0) + 1);
        }
        int maxCount = 0;
        String maxType = null;
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxType = entry.getKey();
            }
        }
        String resultType = maxType;
        System.out.println(Arrays.toString(userVector) + " " + resultType);
    }


    public void check(String filename, List<String> list) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String line;
        int lineNumber = 0;
        int count = 0;
        while ((line = bufferedReader.readLine())!=null){
            String trimLine = line.trim();
            String trimList = list.get(lineNumber).trim();
            if(trimList.equals(trimLine)){
                count++;
            }
            lineNumber++;
        }

        double accuracy = Math.floor(((double) count / lineNumber) * 100);
        System.out.println("Dokładność tej klasyfikacji: " + accuracy + "%");
    }


}
