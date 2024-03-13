import java.util.Arrays;

public class Iris {

    private double[] TrainPoint;

    private double[] TestPoint;
    private double distance;

    private String type;


    public Iris(double[] trainPoint, double[] testPoint, double distance, String type) {
        TrainPoint = trainPoint;
        TestPoint = testPoint;
        this.distance = distance;
        this.type = type;
    }

    public double[] getTrainPoint() {
        return TrainPoint;
    }

    public double getDistance() {
        return distance;
    }

    public double[] getTestPoint() {
        return TestPoint;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TestPoint=" + Arrays.toString(getTestPoint()) +
                ", distance=" + getDistance() +
                ", type='" + getType() + '\'';
    }
}
