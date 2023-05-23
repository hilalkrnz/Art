package com.example.artbook.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.artbook.model.Art;
import com.example.artbook.ui.adapter.ArtAdapter;
import com.example.artbook.util.ArtItemListener;
import com.example.artbook.R;
import com.example.artbook.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ArtItemListener {

    private FragmentHomeBinding binding;
    ArrayList<Art> artArrayList;
    ArtAdapter artAdapter;
    SQLiteDatabase db;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        artArrayList = new ArrayList<>();
        artAdapter = new ArtAdapter(artArrayList, this);
        binding.artBookRv.setAdapter(artAdapter);


        getData();

        fabOnClick();

    }

    public void fabOnClick() {
        binding.addFab.setOnClickListener(view -> {
            Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_anim);
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addArtBookFragment);
            view.startAnimation(animation);
        });

    }

    private void getData() {
        try {
            SQLiteDatabase sqLiteDatabase = requireContext().openOrCreateDatabase("Arts", MODE_PRIVATE, null);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM arts", null);
            int idIx = cursor.getColumnIndex("id");
            int artCaptionIx = cursor.getColumnIndex("artcaption");
            int artTitleIx = cursor.getColumnIndex("arttitle");
            int artPaintTypeIx = cursor.getColumnIndex("artpainttype");
            int imageIx = cursor.getColumnIndex("image");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIx);
                String artCaption = cursor.getString(artCaptionIx);
                String artTitle = cursor.getString(artTitleIx);
                String artPaintType = cursor.getString(artPaintTypeIx);

                byte[] bytesImage = cursor.getBlob(imageIx);
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);


                Art art = new Art(id, artCaption, artTitle, artPaintType, bitmapImage);
                artArrayList.add(art);
            }

            artAdapter.notifyDataSetChanged();

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onArtItemDeleteClicked(int artBookId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            try {
                db = requireContext().openOrCreateDatabase("Arts", MODE_PRIVATE, null);
                db.execSQL("DELETE FROM arts WHERE id = ?", new String[]{String.valueOf(artBookId)});
                System.out.println("successfully deleted");
                artAdapter.notifyItemRemoved(artBookId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}