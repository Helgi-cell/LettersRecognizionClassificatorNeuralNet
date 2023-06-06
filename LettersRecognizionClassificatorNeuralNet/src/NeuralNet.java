
import Entity.network.PredictiveNetwork;
import Serializator.PredictiveNetworkSerializator;

import java.util.ArrayList;
import java.util.List;

public class NeuralNet {

    public NeuralNet() {
        createTestData();
    }

    public static void main(String [] args) {

        NeuralNet neuralNet = new NeuralNet();
        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();
        PredictiveNetwork predictiveNetwork = predictiveNetworkSerializator.getPredictiveNetworkFromFile("fibonacci.net");



        List<List<Double>> inputData = predictiveNetworkSerializator.getInputDataFromFile("fibonacciTestInput.dat");
        List<List<Double>> outputData = predictiveNetworkSerializator.getInputDataFromFile("fibonacciTestOutput.dat");


        for (int i = 0; i < inputData.size() - 1; i++){
            System.out.println("test = " + inputData.get(i) +
                    "      output = " + predictiveNetwork.encountNet(inputData.get(i)) + "   must = " + outputData.get(i));
        }



    }


    public void createTestData(){


        Double [] inputArray = new Double[]
                {987.0, 1597.0, 2584.0, 4181.0, 6765.0, 10946.0, 17711.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0
                };
        Double [] outputArray = new Double[]
                {1597.0, 2584.0, 4181.0, 6765.0, 10946.0, 17711.0, 28657.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0
                };

            List<List<Double>> inputData = new ArrayList<>();
            List<List<Double>> outputData = new ArrayList<>();


        for (int i = 0 ; i < inputArray.length; i++){
            List<Double> learning = new ArrayList<>();
            learning.add(inputArray[i]);
            inputData.add(learning);
        }


        for (int i = 0 ; i < outputArray.length; i++){
            List<Double> learning = new ArrayList<>();
            learning.add(outputArray[i]);
            outputData.add(learning);
        }

        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();

        predictiveNetworkSerializator.writeInputDataToFile(inputData,"fibonacciTestInput.dat");
        predictiveNetworkSerializator.writeInputDataToFile(outputData,"fibonacciTestOutput.dat");

    }


}
