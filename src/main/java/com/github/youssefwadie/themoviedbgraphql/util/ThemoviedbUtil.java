package com.github.youssefwadie.themoviedbgraphql.util;

public final class ThemoviedbUtil {
    public static String imageFullUrl(String imagePath) {
        if (imagePath == null) return null;
        return "https://image.tmdb.org/t/p/original" + imagePath;
    }
}
