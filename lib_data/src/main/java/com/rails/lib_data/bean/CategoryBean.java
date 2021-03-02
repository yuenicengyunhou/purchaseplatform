package com.rails.lib_data.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

/**
 * 商城--分类
 *
 * @author： sk_comic@163.com
 * @date: 2021/2/26
 */
public class CategoryBean extends BaseObservable {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<Boolean> isSel = new ObservableField<>();

}
