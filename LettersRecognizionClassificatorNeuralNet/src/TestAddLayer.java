import Api.FinctionsApi.FunctionEncountingNodesInterface;
import Entity.network.PredictiveNetwork;
import service.SigmoidFunction;

public class TestAddLayer {

    public static void main(String[] args){
        PredictiveNetworkInitAndLearn rootPoint = new PredictiveNetworkInitAndLearn();
        FunctionEncountingNodesInterface func = new SigmoidFunction();
        //FunctionEncountingNodesInterface func = new BipolarSigmoidFunction();

        PredictiveNetwork predictiveNetwork = new PredictiveNetwork(1, 1,
                2, 0.00000000000000000000000000000000000000000000000000000999d
                , 0.15d, func);

        System.out.println(predictiveNetwork + "\n\n");
        predictiveNetwork.incrementNodes();
        predictiveNetwork.incrementNewLayer();
        System.out.println(predictiveNetwork);

    }
}
