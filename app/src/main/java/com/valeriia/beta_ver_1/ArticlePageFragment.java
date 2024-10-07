package com.valeriia.beta_ver_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticlePageFragment extends Fragment {

    private ImageView articleImage;
    private TextView articleTitle;
    private TextView articleTheme;
    private TextView articleText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        articleImage = view.findViewById(R.id.article_page_img);
        articleTitle = view.findViewById(R.id.article_page_title);
        articleTheme = view.findViewById(R.id.article_page_theme);
        articleText = view.findViewById(R.id.article_page_text);

        if (getArguments() != null) {
            String title = getArguments().getString("articleTitle", "Default Title");
            String theme = getArguments().getString("articleTheme", "Default Theme");
            String text = getArguments().getString("articleText", "Default Text");
            int imageResId = getArguments().getInt("articleImage", 0);

            // Set data to views
            articleTitle.setText(title);
            articleTheme.setText(theme);
            articleText.setText(text);
            articleImage.setImageResource(imageResId);

            articleText.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
