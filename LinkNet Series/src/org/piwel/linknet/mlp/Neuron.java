package org.piwel.linknet.mlp;

import java.util.Random;

/**
 * 
 * Cette classe est une mod�lisation d'un neurone. Il est � la base du r�seau neural et du fonctionnement de ce Deep Learning
 * 
 * @author Atomix
 *
 */
public class Neuron {
	 private double[] weights;
     private int nbInputs;

     private double output;

     /**
      * 
      * @return Valeur de la sortie du neurone
      */
     public double getOutput() {
    	 return output;
     }
     /**
      * 
      * @param index Index du poids de la liaison concern�e
      * @return Poids de la liaison concern�e
      */
     public double weight(int index)
     {
         return weights[index];
     }
     /**
      * 
      * @param index Index du poids de la liaison � changer
      * @param value Valeur � attribuer au poids de la liaison
      */
     public void adjustWeight(int index, double value)
     {
         weights[index] = value;
     }
     /**
      * Constructeur de la classe. Il attribut des poids al�atoires aux liaisons
      * @param nbInputs Nombre de neurone d'entr�e dans le r�seau
      */
     public Neuron(int nbInputs)
     {
         this.nbInputs = nbInputs;
         output = Double.NaN;

         Random generator = new Random();

         weights = new double[(this.nbInputs + 1)];
         for(int i = 0; i < (this.nbInputs + 1); i++)
         {
             weights[i] = generator.nextDouble() * 2.0 - 1.0;
         }
     }
     /**
      * Calcule la somme pond�r�e des poids avec les sorties des neurones pr�c�dents pour renvoyer la valeur
      * en appliquant une fonction sigmoide
      * 
      * @param inputs Les valeurs de sortie des neurones du calque pr�c�dent
      * @return La valeur de sortie du neurone
      */
     public double evaluate(double[] inputs)
     {
         if (Double.isNaN(output))
         {
             double x = 0.0;

             for(int i = 0; i < nbInputs; i++)
             {
                 x += inputs[i] * weights[i];
             }
             x += weights[nbInputs];

             output = 1.0 / (1.0 + Math.exp(-1.0 * x));
            
         }
         return output;
     }
     /**
      * 
      * Transforme d'abord point en valeur d'entr�e et applique la fonction <i>evaluate(double[] inputs)</i>
      * 
      * @param point Exemple � �valuer
      * @return La valeur de sortie du neurone
      */
     double evaluate(DataPoint point)
     {
         double[] inputs = point.getInputs();
         return evaluate(inputs);
     }
     /**
      * Nettoie la sortie du neurone
      */
     void clear()
     {
         output = Double.NaN;
     }
}
