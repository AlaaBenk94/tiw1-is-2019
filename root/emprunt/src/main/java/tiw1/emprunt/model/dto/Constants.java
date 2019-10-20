package tiw1.emprunt.model.dto;

public abstract class Constants {
    // Response status
    public static final int OK = 200;
    public static final int ERROR = -200;

    public static final int UNKNOWN_COMMAND = 1001;
    public static final int UNKNOWN_METHOD = 2002;
    public static final int UNKNOWN_PARAM = 3003;

    public final static String ID="ID";
    public final static String ABONNE ="ABONNE";
    public final static String DISPO="DISPO";
    public static final String EMPRUNT = "EMPRUNT";
    public static final String DATE = "DATE";
    public static final String TROTINETTE = "TROTINETTE";


    public static final String ADD = "ADD";
    public static final String REMOVE = "REMOVE";
    public static final String GET = "GET";
    public static final String UPDATE ="UPDATE";
}
