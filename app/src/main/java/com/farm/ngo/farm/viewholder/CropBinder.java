package com.farm.ngo.farm.viewholder;

import com.farm.ngo.farm.model.Crop;


public interface CropBinder {
    public abstract void onBind(Crop c, int position);
}
