//Initial file creator: https://github.com/dadope

package src.models;

//Model class for .../resources/data/scores/*
public class Score {
    public int highscore;
    public int[] scores;

    public String playername;

    public Score(String playername, int[] scores, int highscore){
        this.playername = playername;
        this.highscore = highscore;
        this.scores = scores;
    }
}
