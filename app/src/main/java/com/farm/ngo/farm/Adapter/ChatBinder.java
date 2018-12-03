package com.farm.ngo.farm.Adapter;


import com.farm.ngo.farm.Model.Chat;

public interface ChatBinder {
    public abstract void onBind(Chat c, int position,SwipeController controller);
}
