package com.valeriia.beta_ver_1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.valeriia.beta_ver_1.MainActivity;
import com.valeriia.beta_ver_1.R;
import com.valeriia.beta_ver_1.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    Context context;
    List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleItems = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ArticleAdapter.ArticleViewHolder(articleItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        int imageId = context.getResources().getIdentifier("ic_" + articles.get(position).getImg(), "drawable", context.getPackageName());
        holder.articleImage.setImageResource(imageId);

        holder.articleTheme.setText(articles.get(position).getTheme());
        holder.articleTitle.setText(articles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public static final class ArticleViewHolder extends RecyclerView.ViewHolder{

        ImageView articleImage;
        TextView articleTitle, articleTheme;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            articleImage = itemView.findViewById(R.id.article_img);
            articleTitle = itemView.findViewById(R.id.article_title);
            articleTheme = itemView.findViewById(R.id.article_theme);
        }
    }
}