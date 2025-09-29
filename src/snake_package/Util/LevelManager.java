package snake_package.Util;

public class LevelManager {
    private int levelNumber;
    private int applesToCollect;
    private int obstaclesCount;
    private int trapsCount;

    public LevelManager(int levelNumber, int applesToCollect, int obstaclesCount, int trapsCount) {
        this.levelNumber = levelNumber;
        this.applesToCollect = applesToCollect;
        this.obstaclesCount = obstaclesCount;
        this.trapsCount = trapsCount;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }

    public int getApplesToCollect() {
        return applesToCollect;
    }

    public int getObstaclesCount() {
        return obstaclesCount;
    }

    public int getTrapsCount() {
        return trapsCount;
    }
}