package cu.delrio.yun.friends;
import android.provider.BaseColumns;

public class base_datos {

    public base_datos(){}

    public static abstract class Estructura implements BaseColumns
    {
        public static final String TABLE_NAME = "Usuarios";
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_AGE = "edad";
        public static final String COLUMN_NAME_EMAIL = "correo";
        public static final String COLUMN_NAME_DIR = "dirección";
        public static final String COLUMN_NAME_USER = "usuario";
        public static final String COLUMN_NAME_PASS = "contraseña";
       // public static final byte COLUMN_NAME_IMAGE = Byte.parseByte("image");
    }
}