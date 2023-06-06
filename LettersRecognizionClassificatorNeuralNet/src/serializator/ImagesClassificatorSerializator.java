package serializator;


import service.NeuralNetForImages;

import java.io.*;
import java.util.List;
//import Image.ImagesNeuralNet;

public  class ImagesClassificatorSerializator {
    /*public ClassificatorImagesSerializator() {
    }
*/
    /*
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
    }*/


    public static void writeInputDataToFile(List<NeuralNetForImages> inputData, String filename) {
        List<NeuralNetForImages> dataToFile;
        File file = new File(filename);
        if (file.exists()) {
            dataToFile = getInputDataFromFile(filename);
            for (NeuralNetForImages imagesNeuralNet : inputData) {
                dataToFile.add(imagesNeuralNet);
            }
        } else {
                dataToFile = inputData;
            }


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(dataToFile);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static List<NeuralNetForImages> getInputDataFromFile(String filename){
        List<NeuralNetForImages> inputData = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            List<NeuralNetForImages>  neural = (List<NeuralNetForImages>) ois.readObject();
            inputData = neural;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return inputData;
    }


}
