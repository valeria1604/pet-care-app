package com.valeriia.beta_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.valeriia.beta_ver_1.adapter.ArticleAdapter;
import com.valeriia.beta_ver_1.model.Article;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView articleRecycler;
    private ArticleAdapter articleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Создаем список статей для food
        List<Article> articleList = new ArrayList<>();
        articleList.add(new Article(1, "ABC", "img01", "Food"));
        articleList.add(new Article(2, "DEF", "img02", "Food"));
        articleList.add(new Article(3, "GHM", "img03", "Food"));
        articleList.add(new Article(4, "WAW", "img04", "Food"));

        // Создаем список статей для healthcare
        List<Article> articleList2 = new ArrayList<>();
        articleList2.add(new Article(1, "ABC", "img03", "Healthcare"));
        articleList2.add(new Article(2, "DEF", "img01", "Healthcare"));
        articleList2.add(new Article(3, "GHM", "img04", "Healthcare"));
        articleList2.add(new Article(4, "WAW", "img01", "Healthcare"));

        // Настраиваем RecyclerView
        setArticleRecycler(view, articleList);
        setArticleRecycler2(view, articleList2);

        // TODO: метод один setArticleRecycler сделать, сделать переменную для темы в конструкторе, сделать один список с ифом темы
        return view;
    }

    private void setArticleRecycler(View view, List<Article> articlesList) {
        // Инициализация RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        articleRecycler = view.findViewById(R.id.foodArticleRecycler);  // Найти RecyclerView внутри фрагмента
        articleRecycler.setLayoutManager(layoutManager);

        // Установка адаптера для RecyclerView
        articleAdapter = new ArticleAdapter(getContext(), articlesList);
        articleRecycler.setAdapter(articleAdapter);
    }

    private void setArticleRecycler2(View view, List<Article> articlesList) {
        // Инициализация RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        articleRecycler = view.findViewById(R.id.healthcareArticleRecycler);  // Найти RecyclerView внутри фрагмента
        articleRecycler.setLayoutManager(layoutManager);

        // Установка адаптера для RecyclerView
        articleAdapter = new ArticleAdapter(getContext(), articlesList);
        articleRecycler.setAdapter(articleAdapter);
    }
}