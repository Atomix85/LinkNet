package org.piwel.linknet.mlp;

/**
 * 
 * @author Atomix
 *
 */
public class NeuralNetwork {

	
	 private Neuron[][] hiddenNeurons;
     private Neuron[] outputNeurons;
     private int nbInputs;
     private int[] nbHidden;
     private int nbOutputs;

     /**
      * Constructeur de la class. Il se charge de construire tous les neurones du r�seau.
      * 
      * @param nbInputs Nombre de neurones d'entr�e dans le r�seau
      * @param nbHidden Nombre de neurones dans chaque calques cach�s dans le r�seau
      * @param nbOutputs Nombre de neurones de sortie dans le r�seau
      * 
      */
     
     public NeuralNetwork(int nbInputs, int[] nbHidden, int nbOutputs)
     {
         this.nbHidden = nbHidden;

        
         this.nbInputs = nbInputs;
         this.nbOutputs = nbOutputs;
         
         hiddenNeurons = new Neuron[nbHidden.length][];
         for(int layer = 0; layer < nbHidden.length;layer++) {
             hiddenNeurons[layer] = new Neuron[nbHidden[layer]];
             for(int i = 0; i < nbHidden[layer]; i++)
             {
            	 if(layer == 0)
            		 hiddenNeurons[layer][i] = new Neuron(nbInputs);
            	 else
            		 hiddenNeurons[layer][i] = new Neuron(nbHidden[layer-1]);
             }
         }
         
         outputNeurons = new Neuron[nbOutputs];
         for(int i = 0; i < nbOutputs; i++)
         {
             outputNeurons[i] = new Neuron(nbHidden[nbHidden.length-1]);
         }

     }
     /**
      * 
      * Evaluation destin�e � mettre � jour toutes les valeurs de sortie des neurones du r�seau
      * 
      * @param point Exemple � �valuer
      * @return Tableau des valeurs obtenues pour les neurones de sortie
      */
     public double[] evaluate(DataPoint point)
     {
    	 for(int layer = 0; layer < nbHidden.length;layer++) {
    		 for(Neuron n : hiddenNeurons[layer])
             {
                 n.clear();
             }
    	 }
         for(Neuron n : outputNeurons)
         {
             n.clear();
         }

    	 double[][] hiddenOutputs = new double[nbHidden.length][];
         for(int layer = 0; layer < nbHidden.length;layer++) {
        	 hiddenOutputs[layer] = new double[nbHidden[layer]];
             for(int i = 0; i < nbHidden[layer]; i++)
             {
                 hiddenOutputs[layer][i] = hiddenNeurons[layer][i].evaluate(point);
             }
         }
         

         double[] outputs = new double[nbOutputs];
         for(int outputNb = 0; outputNb < nbOutputs; outputNb++)
         {
             outputs[outputNb] = outputNeurons[outputNb].evaluate(hiddenOutputs[nbHidden.length-1]);
         }
         return outputs;
     }
     /**
      * Ajuste les poids des neurones pour am�liorer la pr�cision et diminuer les erreurs selon le taux d'apprentissage et
      * la vraie valeur devant v�rifier la propri�t�.
      * 
      * @param point Exemple vrai du r�seau
      * @param learningRate Taux d'apprentissage du r�seau (ex : 0.3 : lent mais pr�cis ; 5.0 : rapide mais peut �tre impr�cis)
      * 
      */
     public void adjustWeight(DataPoint point, double learningRate)
     {
         double[] outputDeltas = new double[nbOutputs];
         for(int i = 0; i < nbOutputs; i++)
         {
             double output = outputNeurons[i].getOutput();
             double expectedOutput = point.getOutputs()[i];
             outputDeltas[i] = output * (1 - output) * (expectedOutput - output);
         }

         double[][] hiddenDeltas = new double[nbHidden.length][];
         for(int layer = 0; layer < nbHidden.length;layer++) {
        	 hiddenDeltas[layer] = new double[nbHidden[layer]];
	         for(int i = 0; i < nbHidden[layer]; i++)
	         {
	             double hiddenOutput = hiddenNeurons[layer][i].getOutput();
	             double sum = 0.0;
	             for(int j = 0; j < nbOutputs; j++)
	             {
	                 sum += outputDeltas[j] * outputNeurons[j].weight(i);
	             }
	             hiddenDeltas[layer][i] = hiddenOutput * (1 - hiddenOutput) * sum;
	         }
         }
         double value;

         for(int i = 0; i < nbOutputs; i++)
         {
             Neuron outputNeuron = outputNeurons[i];
             for(int j = 0; j < nbHidden[nbHidden.length-1]; j++)
             {
                 value = outputNeuron.weight(j) + learningRate * outputDeltas[i] * hiddenNeurons[nbHidden.length-1][j].getOutput();
                 outputNeuron.adjustWeight(j, value);
             }

             value = outputNeuron.weight(nbHidden[nbHidden.length-1]) + learningRate * outputDeltas[i] * 1.0;
             outputNeuron.adjustWeight(nbHidden[nbHidden.length-1], value);

         }

         for(int i = 0; i < nbHidden[nbHidden.length-1]; i++)
         {
             Neuron hiddenNeuron = hiddenNeurons[nbHidden.length-1][i];
             for(int j = 0; j < nbInputs; j++)
             {
                 value = hiddenNeuron.weight(j) + learningRate * hiddenDeltas[nbHidden.length-1][i] * point.getInputs()[j];
                 hiddenNeuron.adjustWeight(j, value);
             }

             value = hiddenNeuron.weight(nbInputs) + learningRate * hiddenDeltas[nbHidden.length-1][i] * 1.0;
             hiddenNeuron.adjustWeight(nbInputs, value);
         }
     }
    /**
     *  
     * @return Tableau de tous les neurones du calque cach�
     */
    public Neuron[][] getHiddenNeurons() {
 		return hiddenNeurons;
 	}
    /**
     * 
     * @return Tableau de tous les neurones de sortie
     */
 	public Neuron[] getOutputNeurons() {
 		return outputNeurons;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones d'entr�e dans le r�seau
 	 */
 	public int getNbInputs() {
 		return nbInputs;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones pour chaque calques cach�s dans le r�seau
 	 */
 	public int[] getNbHidden() {
 		return nbHidden;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones de sortie dans le r�seau
 	 */
 	public int getNbOutputs() {
 		return nbOutputs;
 	}
}
