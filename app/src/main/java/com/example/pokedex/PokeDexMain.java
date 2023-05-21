package com.example.pokedex;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class PokeDexMain extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int resumePosition;

    private  ImageView pokeballImg1;
    private  ImageView pokeballImg2;
    private  ImageView typeimg;
    private  ImageView typeimg2;

    private EditText searchBarEditText;
    private Button searchbtn;
    private Button clearbtn;

    private TextView indexIdTextView;
    private TextView nameTextView;

    private TextView typesTextView;
    private TextView hpTextView;
    private TextView attackTextView;
    private TextView defenseTextView;
    private TextView specialAttackTextView;
    private TextView specialDefenseTextView;
    private TextView speedTextView;
    private TextView descET;

    private ProgressBar pbHP;
    private ProgressBar pbAtk;
    private ProgressBar pbDef;
    private ProgressBar pbSpAtk;
    private ProgressBar pbSpDef;
    private ProgressBar pbSpeed;



    private RequestQueue requestQueue;

    private MediaPlayer mediaPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_dex_main);

        listener();
        hideBar();
        music();
        // Start the animation loop
        startAnimationLoop();
        //Fetch Data
        fetch();
    }

    private void fetch() {
        searchbtn.setOnClickListener(v -> {
            searchPokemon();
        });
        clearbtn.setOnClickListener(v -> {
            clearPokemon();
        });

    }

    private void music() {
        //Background Music
        mediaPlayer = MediaPlayer.create(this, R.raw.pokedexbgmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    private void hideBar() {
        // Make gif move
        ImageView gifView = findViewById(R.id.gif_view);
        Glide.with(this).asGif().load(R.drawable.pokedex11).into(gifView);
        // Hide the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void listener() {
        searchBarEditText = findViewById(R.id.searchet);

        searchbtn = findViewById(R.id.searchbtn);
        clearbtn = findViewById(R.id.clearbtn);

        indexIdTextView = findViewById(R.id.indexIDET);
        nameTextView  = findViewById(R.id.pokeNameET);

        typesTextView = findViewById(R.id.typeET);
        hpTextView = findViewById(R.id.hpET);
        attackTextView = findViewById(R.id.attackET);
        defenseTextView = findViewById(R.id.defenseET);
        specialAttackTextView = findViewById(R.id.specialAttackET);
        specialDefenseTextView = findViewById(R.id.specialDefenseET);
        speedTextView = findViewById(R.id.speedET);
        descET = findViewById(R.id.descET);

        pokeballImg1 = findViewById(R.id.pokeball_image);
        pokeballImg2 = findViewById(R.id.pokeball_image2);
        requestQueue = Volley.newRequestQueue(this);

        typeimg = findViewById(R.id.typeimg);
        typeimg2 = findViewById(R.id.typeimg2);

        pbHP = findViewById(R.id.pbHP);
        pbAtk = findViewById(R.id.pbAtk);
        pbDef = findViewById(R.id.pbDef);
        pbSpAtk = findViewById(R.id.pbSpAtk);
        pbSpDef = findViewById(R.id.pbSpDef);
        pbSpeed = findViewById(R.id.pbSpeed);


    }

    private void searchPokemon() {
        String pokemonName = searchBarEditText.getText().toString().toLowerCase();
        if (!pokemonName.isEmpty()) {


            String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int index = response.getInt("id");
                                String name = response.getString("name");
                                name = toTitleCase(name); // convert name to title case

                                JSONArray types = response.getJSONArray("types");
                                String type1 = types.getJSONObject(0).getJSONObject("type").getString("name");
                                if(type1.equalsIgnoreCase("electric")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.electriccry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.electrictype);
                                }
                                else if(type1.equalsIgnoreCase("normal")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.normalcry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.normaltype);
                                }
                                else if(type1.equalsIgnoreCase("fighting")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.fightingcry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.fightingtype);
                                }
                                else if(type1.equalsIgnoreCase("flying")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.giracry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.flyingtype);
                                }
                                else if(type1.equalsIgnoreCase("poison")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.poisoncry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.poisontype);
                                }
                                else if(type1.equalsIgnoreCase("ground")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.groundcry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.groundtype);
                                }
                                else if(type1.equalsIgnoreCase("rock")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.rockcry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.rocktype);
                                }
                                else if(type1.equalsIgnoreCase("bug")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.bugcry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.bugtype);
                                }
                                else if(type1.equalsIgnoreCase("ghost")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.ghostcry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.ghosttype);
                                }
                                else if(type1.equalsIgnoreCase("steel")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.steelcry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.steeltype);
                                }
                                else if(type1.equalsIgnoreCase("fire")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.firecry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.firetype);
                                }
                                else if(type1.equalsIgnoreCase("water")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.watercry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.watertype);
                                }
                                else if(type1.equalsIgnoreCase("grass")) {
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.grasscry);

                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.grasstype);
                                }
                                else if(type1.equalsIgnoreCase("physic")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.psychiccry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.physicaltype);
                                }
                                else if(type1.equalsIgnoreCase("dragon")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.pokecry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.dragontype);
                                }
                                else if(type1.equalsIgnoreCase("dark")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.darkcry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.darktype);
                                }
                                else if(type1.equalsIgnoreCase("fairy")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.clefairy);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.fairytype);
                                }
                                else if(type1.equalsIgnoreCase("physical")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.physicalcry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.physicaltype);
                                }
                                else if(type1.equalsIgnoreCase("special")) {
                                    //Set Pokemon Cry
                                    int audioFile = getIntent().getIntExtra("AUDIO_FILE", R.raw.specialcry);
                                    // Create a new MediaPlayer object and initialize it with the new audio file.
                                    mediaPlayer2 = MediaPlayer.create(PokeDexMain.this, audioFile);
                                    mediaPlayer2.start();
                                    typeimg.setImageResource(R.drawable.specialtype);
                                }
                                type1 = toTitleCase(type1); // convert type1 to title case

                                String type2 = "";
                                if (types.length() > 1) {
                                    type2 = types.getJSONObject(1).getJSONObject("type").getString("name");
                                    if(type2.equalsIgnoreCase("electric")) {
                                        typeimg2.setImageResource(R.drawable.electrictype);
                                    }
                                    else if(type2.equalsIgnoreCase("normal")) {
                                        typeimg2.setImageResource(R.drawable.normaltype);
                                    }
                                    else if(type2.equalsIgnoreCase("fighting")) {
                                        typeimg2.setImageResource(R.drawable.fightingtype);
                                    }
                                    else if(type2.equalsIgnoreCase("flying")) {
                                        typeimg2.setImageResource(R.drawable.flyingtype);
                                    }
                                    else if(type2.equalsIgnoreCase("poison")) {
                                        typeimg2.setImageResource(R.drawable.poisontype);
                                    }
                                    else if(type2.equalsIgnoreCase("ground")) {
                                        typeimg2.setImageResource(R.drawable.groundtype);
                                    }
                                    else if(type2.equalsIgnoreCase("rock")) {
                                        typeimg2.setImageResource(R.drawable.rocktype);
                                    }
                                    else if(type2.equalsIgnoreCase("bug")) {
                                        typeimg2.setImageResource(R.drawable.bugtype);
                                    }
                                    else if(type2.equalsIgnoreCase("ghost")) {
                                        typeimg2.setImageResource(R.drawable.ghosttype);
                                    }
                                    else if(type2.equalsIgnoreCase("steel")) {
                                        typeimg2.setImageResource(R.drawable.steeltype);
                                    }
                                    else if(type2.equalsIgnoreCase("fire")) {
                                        typeimg2.setImageResource(R.drawable.firetype);
                                    }
                                    else if(type2.equalsIgnoreCase("water")) {
                                        typeimg2.setImageResource(R.drawable.watertype);
                                    }
                                    else if(type2.equalsIgnoreCase("grass")) {
                                        typeimg2.setImageResource(R.drawable.grasstype);
                                    }
                                    else if(type2.equalsIgnoreCase("physic")) {
                                        typeimg2.setImageResource(R.drawable.physicaltype);
                                    }
                                    else if(type2.equalsIgnoreCase("dragon")) {
                                        typeimg2.setImageResource(R.drawable.dragontype);
                                    }
                                    else if(type2.equalsIgnoreCase("dark")) {
                                        typeimg2.setImageResource(R.drawable.darktype);
                                    }
                                    else if(type2.equalsIgnoreCase("fairy")) {
                                        typeimg2.setImageResource(R.drawable.fairytype);
                                    }
                                    else if(type2.equalsIgnoreCase("physical")) {
                                        typeimg2.setImageResource(R.drawable.physicaltype);
                                    }
                                    else if(type2.equalsIgnoreCase("special")) {
                                        typeimg2.setImageResource(R.drawable.specialtype);
                                    }
                                    type2 = toTitleCase(type2); // convert type2 to title case
                                } else {
                                    typeimg2.setImageDrawable(null); // Set image view to null if type2 is empty
                                }

                                JSONArray stats = response.getJSONArray("stats");
                                String hp = stats.getJSONObject(0).getString("base_stat");
                                String attack = stats.getJSONObject(1).getString("base_stat");
                                String defense = stats.getJSONObject(2).getString("base_stat");
                                String specialAttack = stats.getJSONObject(3).getString("base_stat");
                                String specialDefense = stats.getJSONObject(4).getString("base_stat");
                                String speed = stats.getJSONObject(5).getString("base_stat");

                                //Set progress bar
                                pbHP.setProgress(Integer.parseInt(hp));
                                pbAtk.setProgress(Integer.parseInt(attack));
                                pbDef.setProgress(Integer.parseInt(defense));
                                pbSpAtk.setProgress(Integer.parseInt(specialAttack));
                                pbSpDef.setProgress(Integer.parseInt(specialDefense));
                                pbSpeed.setProgress(Integer.parseInt(speed));


                                indexIdTextView.setText("Dex #" + index);
                                nameTextView.setText(name);
                                typesTextView.setText(type1 + "/" + type2);
                                hpTextView.setText(hp);
                                attackTextView.setText(attack);
                                defenseTextView.setText(defense);
                                specialAttackTextView.setText(specialAttack);
                                specialDefenseTextView.setText(specialDefense);
                                speedTextView.setText(speed);

                                String imageUrl = response.getJSONObject("sprites").getString("front_default");
                                Picasso.get().load(imageUrl).into(pokeballImg1);
                                Picasso.get().load(imageUrl).into(pokeballImg2);
                                String speciesUrl = response.getJSONObject("species").getString("url").replace("http://", "https://");

                                // Make a request to the species endpoint to get the Pokemon's description
                                JsonObjectRequest speciesRequest = new JsonObjectRequest(Request.Method.GET, speciesUrl, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONArray flavorTextEntries = response.getJSONArray("flavor_text_entries");
                                                    for (int i = 0; i < flavorTextEntries.length(); i++) {
                                                        JSONObject flavorTextEntry = flavorTextEntries.getJSONObject(i);
                                                        String language = flavorTextEntry.getJSONObject("language").getString("name");
                                                        if (language.equals("en")) {
                                                            String description = flavorTextEntry.getString("flavor_text");
                                                            // Remove line breaks and replace special characters
                                                            description = description.replace("\n", " ").replace("\f", " ");
                                                            description = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString();
                                                            descET.setText(description);
                                                            break;
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                                Toast.makeText(PokeDexMain.this, "Error getting Pokemon description", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                requestQueue.add(speciesRequest);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(PokeDexMain.this, "No Pokemon found", Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(request);
        } else {
            Toast.makeText(PokeDexMain.this, "Please enter pokemon name", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearPokemon() {
        searchBarEditText.setText("");
        indexIdTextView.setText("Dex #: ");
        nameTextView.setText("Name: ");
        typesTextView.setText("");
        hpTextView.setText("");
        attackTextView.setText("");
        defenseTextView.setText("");
        specialAttackTextView.setText("");
        specialDefenseTextView.setText("");
        speedTextView.setText("");
        pokeballImg1.setImageResource(R.drawable.pokeball1);
        pokeballImg2.setImageResource(R.drawable.pokeball1);
        typeimg.setImageDrawable(null);
        typeimg2.setImageDrawable(null);
        descET.setText("");

        //Set progress bar
        pbHP.setProgress(0);
        pbAtk.setProgress(0);
        pbDef.setProgress(0);
        pbSpAtk.setProgress(0);
        pbSpDef.setProgress(0);
        pbSpeed.setProgress(0);

    }

    private Handler handler = new Handler();
    private Runnable animateRunnable = new Runnable() {
        @Override
        public void run() {
            AnimationSet animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 20, 0, 0);
            translateAnimation.setDuration(50);
            translateAnimation.setRepeatCount(5);
            translateAnimation.setInterpolator(new CycleInterpolator(0.5f));
            RotateAnimation rotateAnimation = new RotateAnimation(0, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(50);
            rotateAnimation.setRepeatCount(5);
            rotateAnimation.setInterpolator(new CycleInterpolator(0.5f));
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(rotateAnimation);
            pokeballImg1.startAnimation(animationSet);

            // Call the runnable again after the animation finishes
            handler.postDelayed(animateRunnable, 2000);
        }
    };

    // Call this method to start the animation loop
    private void startAnimationLoop() {
        // Start the first iteration immediately
        handler.post(animateRunnable);
    }


    // function to convert a string to title case
    public static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toTitleCase(c));
                capitalizeNext = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
