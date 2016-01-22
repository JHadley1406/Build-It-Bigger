package training.hhi.com.jokelibrary;

import java.util.Random;

/**
 * Created by jrh1406 on 12/17/15.
 */
public class Jokes {
    public String getJokes(){
        Random rand = new Random(System.currentTimeMillis());

        return jokes[rand.nextInt(jokes.length)];
    }

    private String[] jokes = {"How do you know when you are going to drown in milk?\nWhen it's past your eyes"
                            , "Milk is the fastest liquid on earth.\nIt's pasteurized before you even see it"
                            , "A steak pun is a rare medium well done."
                            , "I heard there was a new store called Moderation.\nThey have everything there."
                            , "Why do crabs never give to charity?\nBecause they're shellfish"};

}
