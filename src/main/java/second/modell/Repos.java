package second.modell;

import second.service.Config;

public class Repos {
    private Database_Type database_type;
    private Config config;

    public Repos(Config config){
        this.config = config;
        database_type = config.getDatabase_type();
    }


}
