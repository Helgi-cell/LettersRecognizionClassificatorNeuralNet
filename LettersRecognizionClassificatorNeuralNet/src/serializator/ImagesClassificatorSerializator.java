package serializator;


import Api.NeuralNetApi.NeuralNetI;
import Entity.network.ImageClassificatorNetwork;
import service.NeuralNetForImages;

import java.io.*;
import java.util.List;


public class ImagesClassificatorSerializator {

public static boolean deleteFile(String filename){
    File file = new File(filename);
    Boolean isFileExist = false;
    if (file.isFile()){
        file.delete();

    }
        return isFileExist;
}

    public static void writePredictiveNetworkToFile(ImageClassificatorNetwork neuralNet , String filename) throws IOException {


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(neuralNet);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }


    public static ImageClassificatorNetwork getPredictiveNetworkFromFile(String filename){

        File file = new File(filename);
        Boolean isFileExist = false;

        if (file.exists()){
            isFileExist = true;
        }
        ImageClassificatorNetwork neuralNet = null;
        if(isFileExist) {

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                ImageClassificatorNetwork neural = (ImageClassificatorNetwork) ois.readObject();
                neuralNet = neural;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return neuralNet;
    }



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
