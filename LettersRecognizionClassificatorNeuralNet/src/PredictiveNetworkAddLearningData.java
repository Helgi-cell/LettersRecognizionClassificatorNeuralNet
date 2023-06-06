import Serializator.PredictiveNetworkSerializator;

import java.util.ArrayList;
import java.util.List;

public class PredictiveNetworkAddLearningData {
    List<List<Double>> learningData = new ArrayList<>();
    List<List<Double>> outputData = new ArrayList<>();


    public static void main(String[] args){
        PredictiveNetworkAddLearningData predictiveNetworkAddLearningData = new PredictiveNetworkAddLearningData();
        predictiveNetworkAddLearningData.createLearningData();
    }


    public void createLearningData(){


        Double [] inputArray = new Double[]
                {1.0, 2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0
                };
        Double [] outputArray = new Double[]
                {2.0, 3.0, 5.0, 8.0, 13.0, 21.0, 34.0, 55.0, 89.0, 144.0, 233.0, 377.0, 610.0, 987.0
                };



        for (int i = 0 ; i < inputArray.length; i++){
            List<Double> learning = new ArrayList<>();
            learning.add(inputArray[i]);
            this.learningData.add(learning);
        }


        for (int i = 0 ; i < outputArray.length; i++){
            List<Double> learning = new ArrayList<>();
            learning.add(outputArray[i]);
            this.outputData.add(learning);
        }

        PredictiveNetworkSerializator predictiveNetworkSerializator = new PredictiveNetworkSerializator();

        predictiveNetworkSerializator.writeInputDataToFile(this.learningData,"fibonacciInput.dat");
        predictiveNetworkSerializator.writeInputDataToFile(this.outputData,"fibonacciOutput.dat");

    }

}
