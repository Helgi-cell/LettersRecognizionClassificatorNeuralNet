



import serializator.ImagesClassificatorSerializator;
import service.OperatingImage;
import service.NeuralNetForImages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateImagesFromAllFiles {
    public static void main(String[] args) throws IOException, InterruptedException {


        Character[] letters = new Character[] { 'a'//, 'o', 'i' , 't', 'm' , 'w'

                                                };

        for (int i = 0; i < letters.length; i++){



            List<Double> outByLetter = new ArrayList<>();
            for (int j = 0; j < letters.length; j++) {
                if (j == i) {
                    outByLetter.add(i, 0.99d);
                } else {
                    outByLetter.add(0.01);
                }
            }

            //OperatingImage picture = new OperatingImage(files);
            OperatingImage picture = new OperatingImage(letters[i]);
            List<NeuralNetForImages> arrTemplates = new ArrayList<>();

            for (List<Double> input : picture.learnInputData) {
                arrTemplates.add(new NeuralNetForImages(letters[i], input, outByLetter));
            }

            ImagesClassificatorSerializator.writeInputDataToFile(arrTemplates, "learnClass_" + letters[i] + ".net");
            arrTemplates = ImagesClassificatorSerializator.getInputDataFromFile("learnClass_" + letters[i] + ".net");

            System.out.println(arrTemplates.size());
        }

    }

}

