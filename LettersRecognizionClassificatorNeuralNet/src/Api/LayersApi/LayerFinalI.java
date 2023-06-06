package Api.LayersApi;

import java.util.List;

public interface LayerFinalI extends LayerCommonI {

    public List<Double> addWeightsByNewNeuronInPrevLayer(Integer prevNeuronsNum);
    public void changeWeigths();
}
