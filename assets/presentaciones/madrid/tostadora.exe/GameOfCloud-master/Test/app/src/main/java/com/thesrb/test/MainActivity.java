package com.thesrb.test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private TextView pregunta, r1, r2, r3, r4;
    private List<Pregunta> preguntas = new ArrayList<>();
    private long tiempo = 0;
    private int index = 0;
    private int selecion = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pregunta = findViewById(R.id.txt_pregunta);
        r1 = findViewById(R.id.b1);
        r2 = findViewById(R.id.b2);
        r3 = findViewById(R.id.b3);
        r4 = findViewById(R.id.b4);

        new Peticion().execute();

        new CountDownTimer(999999999, 1000){

            @Override
            public void onFinish() {
            }

            @Override
            public void onTick(long l) {
                new Posicion().execute();
            }
        }.start();
    }

    public void posi(View v){
        new Posicion().execute();
    }

    class Posicion extends AsyncTask<Void, Void, int[]>{

        @Override
        protected int[] doInBackground(Void... voids) {
            try{
                HttpGet get = new HttpGet("http://192.168.43.167/goc/getCurrentResponse.php");
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                String respuesta = EntityUtils.toString(entity);
                int resp [] = {Integer.parseInt(respuesta.charAt(0)+"" ), Integer.parseInt(respuesta.charAt(1)+"")};
                return resp;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(int[] ints) {
            if (ints!= null)
                selecciona(ints[0], ints[1]==1);
        }
    }

    class Peticion extends AsyncTask<Void, Void, List<Pregunta>>{

        @Override
        protected void onPreExecute() {
            pregunta.setText("Pidiendo preguntas...");
        }

        @Override
        protected List<Pregunta> doInBackground(Void... voids) {
            try{
                HttpGet get = new HttpGet("http://192.168.43.167/goc/getQuestions.php");
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                String respuesta = EntityUtils.toString(entity);
                String preguntas[] = respuesta.split("\\r\\n|\\n|\\r");
                List<Pregunta> preguntasLista = new ArrayList<>();
                for (String p:preguntas)
                    preguntasLista.add(new Pregunta(p.split(";")));
                return preguntasLista;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Pregunta> p) {
            preguntas = p;
            if (p != null)
                ponPregunta(preguntas.get(index));


        }
    }

    private void siguiente(){

        r1.setBackgroundColor(Color.WHITE);
        r2.setBackgroundColor(Color.WHITE);
        r3.setBackgroundColor(Color.WHITE);
        r4.setBackgroundColor(Color.WHITE);
        index++;
        selecion = 0;
       // Toast.makeText(this, "Siguiente pregunta: " + index, Toast.LENGTH_SHORT).show();
        ponPregunta(preguntas.get(index));
    }

    private void ponPregunta(Pregunta p){
        //Toast.makeText(this, p.toString(), Toast.LENGTH_LONG).show();
        pregunta.setText(p.p);
        r1.setText(p.r1);
        r2.setText(p.r2);
        r3.setText(p.r3);
        r4.setText(p.r4);
    }

    private void selecciona(int index, boolean seleccionado){
        r1.setBackgroundColor(Color.WHITE);
        r2.setBackgroundColor(Color.WHITE);
        r3.setBackgroundColor(Color.WHITE);
        r4.setBackgroundColor(Color.WHITE);

        if (seleccionado)
            selecion = index;

        int color = (selecion!=index ? Color.GREEN : Color.YELLOW);

        TextView selec = dameRespuesta(index);
        String resp = selec.getText().toString();
        selec.setBackgroundColor(color);

        if (selecion != 0){
            tiempo += 1;
            if (tiempo == 4){
                tiempo = 0;
                if (resp.equals(preguntas.get(this.index).respC))
                    siguiente();
                else{
                    selec.setBackgroundColor(Color.RED);
                }
            }
        }else{
            tiempo = 0;
        }



    }

    private TextView dameRespuesta (int index){
        switch (index){
            case 1:
                return r1;
            case 2:
                return r2;
            case 3:
                return r3;
            case 4:
                return r4;
        }
        return null;
    }

    class Pregunta{
        String p, r1, r2, r3, r4, respC;
        Pregunta (String args[]){
            respC = args[1];
            p = "¿" + args[0] + "?";

            List<String> lista = new ArrayList<>();
            lista.add(args[1]);
            lista.add(args[2]);
            lista.add(args[3]);
            lista.add(args[4]);
            Collections.shuffle(lista);

            r1 = lista.get(0);
            r2 = lista.get(1);
            r3 = lista.get(2);
            r4 = lista.get(3);
        }

        @Override
        public String toString() {
            return p + r1+r2+r3+r4+respC;
        }
    }
}
