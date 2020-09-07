package com.spring.viagemapp.utils;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

//Fontes: https://www.tutorialspoint.com/mahout/mahout_recommendation.htm
//https://mahout.apache.org/users/recommender/userbased-5-minutes.html
public class Recomendador {

    private static Recomendador instancia;

    private Recomendador() {
    }

    public static Recomendador getInstance() {
        if(instancia == null) {
            instancia = new Recomendador();
        }

        return instancia;
    }

    public List<RecommendedItem> recomendar(String filename){
        try {
            //Creating data model
            System.out.println(filename);
            DataModel datamodel = new FileDataModel(new File(filename)); //data

            //Creating UserSimilarity object.
            UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);

            //Creating UserNeighbourHHood object.
            UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(0.1, usersimilarity, datamodel);

            //Create UserRecomender
            UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);

            List<RecommendedItem> recommendations = recommender.recommend(2, 3);


            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
