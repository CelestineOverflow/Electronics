import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Electronics {
    private ArrayList<Double> values;
    private static int steps = 15;
    private static double voltageGateSourceMin = 1.5;
    private static double voltageGateSourceMax = 3;
    private static double voltageThreshold = 1.8;
    private static double CurrentDSS = 0.6;
    public Electronics(){
        values = computeValues();
    }
    public ArrayList<Double> computeValues(){
        ArrayList<Double> computed = new ArrayList<>();
        double voltageGateSource = voltageGateSourceMin;
        double stepVoltage = (voltageGateSourceMax - voltageGateSourceMin) / steps;
        for (int i = 0; i <= steps; i++){
            if(voltageGateSource > voltageThreshold){
                double currentDrain = CurrentDSS * (1-(voltageGateSource/voltageThreshold)) * (1-(voltageGateSource/voltageThreshold));
                computed.add(currentDrain);
            } else {
                computed.add(0.0);
            }
            voltageGateSource+=stepVoltage;
        }
        return computed;
    }
    public void printResults(){
        double voltageGateSource = voltageGateSourceMin;
        double stepVoltage = (voltageGateSourceMax - voltageGateSourceMin) / steps;
        for (double value : values){
            System.out.printf("Ugs = %.2f Ids = %.2e\n", voltageGateSource, value);
            voltageGateSource+=stepVoltage;
        }
    }
    public void outputFile(){
        try {
            FileWriter myWriter = new FileWriter("output.csv");
            //
            double voltageGateSource = voltageGateSourceMin;
            double stepVoltage = (voltageGateSourceMax - voltageGateSourceMin) / steps;
            myWriter.write("Ugs,Ids,\n");
            for (double value : values){
                myWriter.write(String.valueOf(voltageGateSource) + ",");
                myWriter.write(String.valueOf(value) + "\n");
                voltageGateSource+=stepVoltage;
            }
            //
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Electronics electronics = new Electronics();
        electronics.printResults();
        electronics.outputFile();
    }
}
