package cu.delrio.yun.friends;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    SQLBD basedatos;
    private EditText Nombre;
    private EditText Email;
    private EditText Dir;
    private EditText user;
    private EditText userPassword;
    private Button btnGuardar;
    private String element_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Nombre = findViewById(R.id.Nombre);
        Email = findViewById(R.id.Email);
        Dir = findViewById(R.id.Dir);
        user = findViewById(R.id.user);
        userPassword = findViewById(R.id.userPassword);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_datas(Nombre, Email, Dir, user, userPassword);
            }
        });
    }

    private void checkpass(EditText Nombre, EditText Email, EditText Dir, EditText user, EditText userPassword){
        if (userPassword.getText().toString().length() < 10) {
            AlertDialog.Builder buiider = new AlertDialog.Builder(this);
            buiider.setMessage("La contraseña debe tener almenos 10 dígitos.")
                    .setTitle("Error!")
                    .setNeutralButton("",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
            AlertDialog alert = buiider.create();
            alert.show();
        }else{
            save_datas(Nombre, Email, Dir, user, userPassword);
        }

    }
    private void save_datas(EditText Nombre, EditText Email, EditText Dir, EditText user, EditText userPassword) {
        basedatos = new SQLBD(this);
        SQLiteDatabase sqlite = basedatos.getWritableDatabase();
        String nombre = Nombre.getText().toString();
        String email = Email.getText().toString();
        String user_db = user.getText().toString();
        String pass = userPassword.getText().toString();
        String dir = Dir.getText().toString();
        ContentValues content = new ContentValues();

        String[] columnas = {
                base_datos.Estructura._ID,
                base_datos.Estructura.COLUMN_NAME_USER,
                base_datos.Estructura.COLUMN_NAME_PASS
        };
        String my_user_bd = base_datos.Estructura.COLUMN_NAME_USER + " LIKE '" +  user_db + "'";
        String ordenSalida = base_datos.Estructura.COLUMN_NAME_USER + " DESC";
        Cursor cursor = sqlite.query(base_datos.Estructura.TABLE_NAME, columnas, my_user_bd,null , null, null, ordenSalida);
        if(cursor.getCount() != 0) {
            AlertDialog.Builder buiider = new AlertDialog.Builder(this);
            buiider.setMessage("El usuario ya existe.")
                    .setTitle("Error!")
                    .setNeutralButton("",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
            AlertDialog alert = buiider.create();
            alert.show();
        }else {
            if (!nombre.isEmpty()) {
                content.put(base_datos.Estructura.COLUMN_NAME_NAME, nombre);
            }
            if (!email.isEmpty()) {
                content.put(base_datos.Estructura.COLUMN_NAME_EMAIL, email);
            }
            if (!user_db.isEmpty()) {
                content.put(base_datos.Estructura.COLUMN_NAME_USER, user_db);
            }
            if (!pass.isEmpty()) {
                content.put(base_datos.Estructura.COLUMN_NAME_PASS, pass);
            }
            if (!dir.isEmpty()) {
                content.put(base_datos.Estructura.COLUMN_NAME_DIR, dir);
            }
            if (user_db.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Usuario y contraseña requeridos.", Toast.LENGTH_LONG).show();
            } else if (pass.length() < 10) {
                AlertDialog.Builder buiider = new AlertDialog.Builder(this);
                buiider.setMessage("La contraseña debe tener almenos 10 dígitos.")
                        .setTitle("Error!")
                        .setNeutralButton("",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alert = buiider.create();
                alert.show();
            } else {
                sqlite.insert(base_datos.Estructura.TABLE_NAME, null, content);
                cursor = sqlite.query(base_datos.Estructura.TABLE_NAME, columnas, my_user_bd, null, null, null, ordenSalida);
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    long identificador = cursor.getLong(cursor.getColumnIndex(base_datos.Estructura._ID));
                    sqlite.close();
                    Intent intent = new Intent(Register.this, Perfil.class);
                    intent.putExtra("element_id", identificador);
                    startActivity(intent);
                }
            }
        }
    }
}
