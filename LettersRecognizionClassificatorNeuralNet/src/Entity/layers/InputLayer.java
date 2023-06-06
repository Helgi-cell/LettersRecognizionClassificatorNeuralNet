package Entity.layers;

import Api.LayersApi.LayerInputI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputLayer implements LayerInputI, Serializable {

    private List<Double> neurons = new ArrayList<>();
    public InputLayer(Integer numNeurons) {
         initLayer(numNeurons);
    }

     private void initLayer(Integer numNeurons) {
        for (int i = 0; i < numNeurons; i++) {
                this.neurons.add(0.0);
        }
    }

/*    @Override
    public List<Double> encountLayer(List<Double> templateOfLearning) {
        if (templateOfLearning.size() == neurons.size()){
            for (int i = 0; i < neurons.size(); i++){
                Double value = templateOfLearning.get(i);
                neurons.set(i, value);
            }
        }
        return templateOfLearning;
    }*/

    public List<Double> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Double> neurons) {
        this.neurons = neurons;
    }

    @Override
    public String toString() {
        return "InputLayer{" +
                "\n neurons=" + neurons +
                "}\n";
    }


    @Override
    public List<Double> encountNeuron(List<Double> templateOfLearning) {
        if (templateOfLearning.size() == neurons.size()){
            for (int i = 0; i < neurons.size(); i++){
                Double value = templateOfLearning.get(i);
                neurons.set(i, value);
            }
        }
        return templateOfLearning;
    }
}
