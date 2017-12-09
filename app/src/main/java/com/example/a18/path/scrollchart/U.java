package com.example.a18.path.scrollchart;

/**
 * Created by pdog on 2017/12/8.
 */
class U <T>{

    T body;
    boolean dark;

    public U(T body) {
        this.body = body;
    }


    public T getBody(){
        return body;
    }

    public boolean isDark() {
        return dark;
    }

    public void setDark(boolean dark) {
        this.dark = dark;
    }

}
