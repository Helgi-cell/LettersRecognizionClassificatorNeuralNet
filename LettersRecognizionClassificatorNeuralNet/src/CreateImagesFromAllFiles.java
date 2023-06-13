



import serializator.ImagesClassificatorSerializator;
import service.OperatingImage;
import service.NeuralNetForImages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateImagesFromAllFiles {
    public static void main(String[] args) throws IOException, InterruptedException {


        Character[] letters = new Character[] { 'a', 'o', 'i' , 't', 'm' , 'w'

                                                };



        for (Character letter : letters) {

            List<Double> outByLetter = new ArrayList<>();
            outByLetter.add(1.0d);


            //OperatingImage picture = new OperatingImage(files);
            OperatingImage picture = new OperatingImage(letter);
            List<NeuralNetForImages> arrTemplates = new ArrayList<>();

            for (List<Double> input : picture.learnInputData) {
                arrTemplates.add(new NeuralNetForImages(letter, input, outByLetter));
            }

            ImagesClassificatorSerializator.writeInputDataToFile(arrTemplates, "learnClass_" + letter + ".net");
            arrTemplates = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letter + ".net");

            System.out.println(arrTemplates);
        }

    }

}

