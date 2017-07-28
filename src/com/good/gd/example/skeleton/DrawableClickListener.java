package com.good.gd.example.skeleton;

/**
 * Created by developer01 on 03/07/2017.
 */
public interface DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}
