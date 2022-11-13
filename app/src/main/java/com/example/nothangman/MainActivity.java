package com.example.nothangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextInputEditText guessW;
    TextView correctW;

    wordsL list;
    game newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessW = (TextInputEditText) findViewById(R.id.input);
        correctW = (TextView) findViewById(R.id.guessW);
        Button restart = (Button) findViewById(R.id.restart);
        Button exit = (Button) findViewById(R.id.exit);

        list = new wordsL();
        String randomW = list.getRandomW();
        newGame = new game(randomW);
        inLayout(list.randomWLength());

        guessW.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    String letter = Objects.requireNonNull(guessW.getText()).toString().trim();

                    if (letter.length() > 0){
                        guessW.setText("");

                        if (!newGame.checkDuplicate(letter)){
                            newGame.triedL(letter);

                            if(!newGame.checkExisted(letter)){
                                newGame.addWrong(letter);
                                addWrongView(letter);
                            }else{
                                updateUnder(letter, randomW);
                            }
                        }else{
                            showToast(letter + "is already existed");
                        }
                    }else{
                        showToast("Value empty");
                    }
                    InputMethodManager method = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    method.hideSoftInputFromWindow(guessW.getWindowToken(), 0);

                    gameStatus();
                }
            }
            return true;
        });
        restart.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });
        exit.setOnClickListener(v -> finish());
    }
    private void inLayout(int input){
        StringBuilder word = new StringBuilder();
        for (int i=0; i<input; i++){
            if (i<input-1){
                word.append("_ ");
            }else{
                word.append("_");
            }
        }
        correctW.setText(word.toString());
        correctW.setTextSize(28); //correct word text size
    }
    private void addWrongView(String letter){
        TextView wrongLView = (TextView) findViewById(R.id.incorrectL);
        String temp = wrongLView.getText() + letter + " ";
        wrongLView.setText(temp);
    }
    public void updateUnder(String letter, String word){
        char letterC = letter.charAt(0);
        int wordL = word.length();

        String correctW = this.correctW.getText().toString();
        String correctW_s = correctW.replaceAll("\\s", "");
        StringBuilder newW = new StringBuilder();

        for (int i=0; i<wordL; i++){
            if (word.charAt(i) == letterC || word.charAt(i) == Character.toUpperCase(letterC)){
                newW.append(letterC);
            }else{
                if (correctW_s.charAt(i) == '_'){
                    newW.append("_");
                }else{
                    newW.append(word.charAt(i));
                }
            }
        }
        this.correctW.setText(newW.toString().replaceAll("\\B", " "));
    }
    public void gameStatus(){
        boolean state = false;
        if (checkUnder() && newGame.getTriesLeft() == newGame.getTries()){
            showToast(getString(R.string.lost));
            state = true;
        }
        if (!checkUnder()){
            showToast(getString(R.string.won));
            state = true;
        }
        if (state){
            disableInput();
        }
    }
    public void disableInput(){
        guessW.setVisibility(View.INVISIBLE);
    }
    public boolean checkUnder(){ //return 1 if _ existed
        TextView word = (TextView) findViewById(R.id.guessW);
        return word.getText().toString().contains("_");
    }
    public void showToast(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}