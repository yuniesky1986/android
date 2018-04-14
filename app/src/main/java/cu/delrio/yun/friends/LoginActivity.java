package cu.delrio.yun.friends;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import cu.delrio.yun.friends.base_datos.Estructura;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private Button Login;
    private Button Register;
    private SQLBD basedatos;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.userName);
        pass = findViewById(R.id.userPassword);
        Login = findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(user.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void validate(String user, String pass) {
        basedatos = new SQLBD( this);
        SQLiteDatabase sqlite = basedatos.getReadableDatabase();
        String ordenSalida = Estructura.COLUMN_NAME_USER + " DESC";
        if(user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this,"Usuario y contraseña requeridos.", Toast.LENGTH_LONG).show();
        }else
        {
            String[] columnas = {
                    Estructura._ID,
                    Estructura.COLUMN_NAME_USER,
                    Estructura.COLUMN_NAME_PASS
            };
            String whereClause = Estructura.COLUMN_NAME_USER + "=?" + " AND " + Estructura.COLUMN_NAME_PASS + "=?";
            String[] whereArgs = new String[]{user, pass};
            Cursor cursor = sqlite.query(Estructura.TABLE_NAME, columnas, whereClause, whereArgs , null, null, ordenSalida);
            if(cursor.getCount() != 0)
            {
                cursor.moveToFirst();
                long identificador = cursor.getLong(cursor.getColumnIndex(Estructura._ID));
                //Toast.makeText(this, "Los datos del Usuario " +  user , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, Perfil.class);
                intent.putExtra("element_id", identificador);
                startActivity(intent);
            }
            else
            {
                AlertDialog.Builder buiider = new AlertDialog.Builder(this);
                buiider.setMessage("Usuario o contraseña no válido.")
                        .setTitle("Error!")
                        .setNeutralButton("Cerrar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                AlertDialog alert = buiider.create();
                alert.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_register) {
            register();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register() {
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
    }
}
