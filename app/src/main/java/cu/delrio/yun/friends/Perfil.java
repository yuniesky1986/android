package cu.delrio.yun.friends;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cu.delrio.yun.friends.base_datos.Estructura;

public class Perfil extends AppCompatActivity {

    SQLBD basedatos;
    private EditText Nombre;
    private EditText Email;
    private EditText Dir;
    private EditText user;
    private EditText userPassword;
    private String element_id = "";
    private Button btnGuardar;
    private ImageButton imageButton;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Nombre = findViewById(R.id.Nombre);
        Email = findViewById(R.id.Email);
        Dir = findViewById(R.id.Dir);
        user = findViewById(R.id.user);
        userPassword = findViewById(R.id.userPassword);
        basedatos = new SQLBD( this);
        final SQLiteDatabase sqlite = basedatos.getReadableDatabase();
        element_id = getIntent().getExtras().get("element_id").toString();

        if (!element_id.isEmpty()){
            String[] columnas = {
                    Estructura._ID,
                    Estructura.COLUMN_NAME_USER,
                    Estructura.COLUMN_NAME_PASS,
                    Estructura.COLUMN_NAME_EMAIL,
                    Estructura.COLUMN_NAME_DIR,
                    Estructura.COLUMN_NAME_NAME
            };
            String element_id_bd = Estructura._ID + " LIKE '" +  element_id + "'";
            Cursor cursor = sqlite.query(Estructura.TABLE_NAME, columnas, element_id_bd,null , null, null, null);
            if(cursor.getCount() != 0){
                cursor.moveToFirst();
                Nombre.setText(cursor.getString(cursor.getColumnIndex(Estructura.COLUMN_NAME_NAME)));
                Email.setText(cursor.getString(cursor.getColumnIndex(Estructura.COLUMN_NAME_EMAIL)));
                Dir.setText(cursor.getString(cursor.getColumnIndex(Estructura.COLUMN_NAME_DIR)));
                user.setText(cursor.getString(cursor.getColumnIndex(Estructura.COLUMN_NAME_USER)));
                userPassword.setText(cursor.getString(cursor.getColumnIndex(Estructura.COLUMN_NAME_PASS)));
            }
            /*imageButton = (ImageButton) findViewById(R.id.image);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });*/

            btnGuardar = findViewById(R.id.btnGuardar);
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    save_datas(Nombre, Email, Dir, user, userPassword, element_id);
                }
            });
        }
    }
    private void save_datas(EditText Nombre, EditText Email, EditText Dir, EditText userbd, EditText userPassword, String element_id) {
        basedatos = new SQLBD(this);
        SQLiteDatabase sqlite = basedatos.getWritableDatabase();
        String nombre = Nombre.getText().toString();
        String email = Email.getText().toString();
        String dir = Dir.getText().toString();
        String myuser = userbd.getText().toString();
        String pass = userPassword.getText().toString();
        ContentValues content = new ContentValues();
        if (!nombre.isEmpty()) {
            content.put(base_datos.Estructura.COLUMN_NAME_NAME, nombre);
        }
        if (!email.isEmpty()){
            content.put(base_datos.Estructura.COLUMN_NAME_EMAIL, email);
        }
        if (!myuser.isEmpty()){
            content.put(base_datos.Estructura.COLUMN_NAME_USER, myuser);
        }
        if (!pass.isEmpty()){
            content.put(base_datos.Estructura.COLUMN_NAME_PASS, pass);
        }
        if (!dir.isEmpty()){
            content.put(base_datos.Estructura.COLUMN_NAME_DIR, dir);
        }
        sqlite.update(Estructura.TABLE_NAME, content, "_id=" + element_id, null);
        Toast.makeText(this, "Datos actualizados.", Toast.LENGTH_LONG).show();
        sqlite.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* If a user is currently authenticated, display a logout menu */
        if (this.element_id != null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }else if (id == R.id.action_map){
            viewmap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        element_id = "";
        intent.putExtra("element_id", element_id);
        startActivity(intent);
    }

    private void viewmap() {
        Intent intent = new Intent(Perfil.this, MapsActivity.class);
     /*   element_id = "";
        intent.putExtra("element_id", element_id);*/
        startActivity(intent);
    }
}
