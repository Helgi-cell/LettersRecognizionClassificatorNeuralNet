package Serializator;

import Api.NeuralNetApi.NeuralNetI;
import Entity.network.PredictiveNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class PredictiveNetworkSerializator {

    public void writePredictiveNetworkToFile(PredictiveNetwork neuralNet , String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(neuralNet);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    public PredictiveNetwork getPredictiveNetworkFromFile(String filename){
        PredictiveNetwork neuralNet = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            PredictiveNetwork  neural = (PredictiveNetwork) ois.readObject();
            neuralNet = neural;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return neuralNet;
    }


    public void writeInputDataToFile(List<List <Double>> inputData, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(inputData);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    public List<List<Double>> getInputDataFromFile(String filename){
        List<List<Double>> inputData = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            List<List<Double>>  neural = (List<List<Double>>) ois.readObject();
            inputData = neural;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return inputData;
    }


}

