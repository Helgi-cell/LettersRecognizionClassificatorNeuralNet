package Api.NeuralNetApi;

import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Api.LayersApi.LayerCommonI;

import java.util.List;

public interface NeuralNetI {
    List<LayerCommonI> initNetwork(Integer numNeuronsInputLayer, Integer nimNeuronsOutputLayer,
                                          Integer numHiddenLayers);
    List<Double> encountNet(List<Double> templateOfLearning);
    List<List<Double>> encountDerivatives();
    Double encountWeight();
    List<LayerCommonI> incrementNodes();

    List<List<Double>> encountNetErrors(List <Double> inputTemplateOfLearning,List<Double> outputTemplateOfLearning);

    Double encountMidSquareError (List<List<Double>> inputLearningData, List<List<Double>> outputLearningData);

    Double encountMaxError (List<List<Double>> inputLearningData, List<List<Double>> outputLearningData);

    Double getMidSquareError  ();

    Integer getNumberNeuronsInHiddenLayer();

    Integer getNumberHiddenLayers();

    List<LayerCommonI> incrementNewLayer();
}
