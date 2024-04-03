package com.example.searchvideogames.Fragments;

import static com.example.searchvideogames.services.DataService.getGamesByRating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.searchvideogames.R;
import com.example.searchvideogames.adapters.GameAdapter;
import com.example.searchvideogames.models.Game;
import com.example.searchvideogames.services.DataService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAllGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllGames extends Fragment {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private DataService dataService;

    private Spinner genreSpinner;

    private Spinner yearSpinner;



    private ArrayList<Game> allGames; // Store all games
    private ArrayList<Game> filteredGames; // Store filtered games

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAllGames() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAllGames.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAllGames newInstance(String param1, String param2) {
        FragmentAllGames fragment = new FragmentAllGames();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_games, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        genreSpinner = view.findViewById(R.id.genreSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.genre_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        yearSpinner = view.findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        dataService = new DataService();
        allGames = dataService.getArrGame(); // Get all games
        filteredGames = new ArrayList<>(allGames); // Initially, display all games

        gameAdapter = new GameAdapter(filteredGames); // Use filtered games
        recyclerView.setAdapter(gameAdapter);

        Button topGamesButton = view.findViewById(R.id.topGamesButton);


        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedYear = parentView.getItemAtPosition(position).toString();
                if (!selectedYear.equalsIgnoreCase("All Years")) {
                    // If a specific year is selected, filter games by year
                    if (selectedYear.length() >= 4) {
                        String yearSubstring = selectedYear.substring(0, 4); // Parse the selected year to a 4-digit string
                        filteredGames.clear();
                        filteredGames.addAll(dataService.getGamesByYear(yearSubstring));
                    }
                } else {
                    // If "All Years" is selected, show all games
                    filteredGames.clear();
                    filteredGames.addAll(allGames);
                }
                gameAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });



        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedGenre = parentView.getItemAtPosition(position).toString();
                if (selectedGenre.equalsIgnoreCase("All Games")) {
                    // If "All Games" selected, show all games
                    filteredGames.clear();
                    filteredGames.addAll(allGames);
                } else {
                    // If a specific genre selected, filter games by genre
                    filteredGames.clear();
                    filteredGames.addAll(dataService.getGamesByGenre(selectedGenre));
                }
                gameAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }



        });

        topGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to get top-rated games
                ArrayList<Game> topRatedGames = getGamesByRating();

                // Update your UI to display the top-rated games
                filteredGames.clear(); // Clear the previous filtered games
                filteredGames.addAll(topRatedGames); // Add the new top-rated games
                gameAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }
        });

        return view;
    }



}