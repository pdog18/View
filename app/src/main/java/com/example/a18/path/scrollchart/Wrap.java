package com.example.a18.path.scrollchart;

/**
 * Created by pdog on 2017/12/8.
 */
class Wrap<T>{

    T body;
    boolean dark;

    public Wrap(T body) {
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
