package com.farm.ngo.farm.Holder;

import com.farm.ngo.farm.Model.Crop;


public interface CropBinder {
    public abstract void onBind(Crop c, int position);
}
